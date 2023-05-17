import React, { useEffect, useRef, useState } from 'react'
import styles from './SearchMtPage.module.scss'
import { useLocation, useNavigate } from 'react-router-dom'
import { getMountains } from 'apis/services/mountains'
import MtList from 'components/common/MtList'
import Loading from 'components/common/Loading'
import useDebounce from 'hooks/useDebounce'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { MtInfo } from 'types/mt.interface'

function SearchMtPage() {
  const navigate = useNavigate()

  const [mtInfoArray, setMtInfoArray] = useState<MtInfo[]>([]) // 산 정보 배열
  const isEnd = useRef(true) // 무한스크롤 마지막 정보 여부
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 useRef

  // 뒤로가기 클릭 시, 메인 페이지로 이동시키는 리스너
  useEffect(() => {
    const toMainPage = () => navigate('/main')
    window.addEventListener('popstate', toMainPage)
    return () => window.removeEventListener('popstate', toMainPage)
  }, [])

  // url 주소의 sate로부터 query를 전달 받음
  const queryClient = new URLSearchParams(useLocation().search)
  const query = queryClient.get('query') || ''
  const debouncedQuery = useDebounce(query)

  // debouncedQuery에 따라서 산 검색 api 요청
  useEffect(() => {
    if (debouncedQuery) {
      getMountains(debouncedQuery)
        .then((res) => {
          setMtInfoArray(res.data.result.content)
          isEnd.current = !res.data.result.hasNext
          if (infiniteRef.current) {
            infiniteRef.current.scrollTo({
              top: 0,
              left: 0,
              behavior: 'smooth',
            })
          }
        })
        .catch(() => {})
    }
  }, [debouncedQuery])

  // 무한 스크롤 api 요청 함수
  function loadMore() {
    return getMountains(debouncedQuery, mtInfoArray.slice(-1)[0].mountainId)
      .then((res) => {
        setMtInfoArray((mtInfoArray) => [
          ...mtInfoArray,
          ...res.data.result.content,
        ])
        isEnd.current = !res.data.result.hasNext
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({
    ref: infiniteRef,
    loadMore,
    isEnd: isEnd.current,
  })

  return (
    <div ref={infiniteRef} className={`p-md ${styles.container}`}>
      <MtList mtInfoArray={mtInfoArray} size="lg" />
      {isLoading && (
        <div className={styles.loading}>
          <Loading size="sm" />
        </div>
      )}
    </div>
  )
}

export default React.memo(SearchMtPage)
