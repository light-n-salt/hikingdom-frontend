import React, { useEffect, useRef, useState } from 'react'
import styles from './SearchMtPage.module.scss'
import { useLocation, useNavigate } from 'react-router-dom'
import { getMountains } from 'apis/services/mountains'
import MtList from 'components/common/MtList'
import Loading from 'components/common/Loading'
import useDebounce from 'hooks/useDebounce'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { MtInfo } from 'types/mt.interface'

type OutletProps = {
  value: string
}

function SearchMtPage() {
  const navigate = useNavigate()

  const [mtInfoArray, setMtInfoArray] = useState<MtInfo[]>(mtInfoEx) // 산 정보 배열
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 useRef

  // 뒤로가기 클릭 시, 메인 페이지로 이동시키는 리스너
  useEffect(() => {
    const toMainPage = () => navigate('/main')
    window.addEventListener('popstate', toMainPage)
    return () => window.removeEventListener('popstate', toMainPage)
  }, [])

  // url 주소의 sate로부터 query를 전달 받음
  const location = useLocation()
  const query = location.state.query
  const debouncedQuery = useDebounce(query!)

  // debouncedQuery에 따라서 산 검색 api 요청
  useEffect(() => {
    if (debouncedQuery) {
      getMountains(debouncedQuery)
        .then((res) => {
          setMtInfoArray(res.data.result)
        })
        .catch(() => {})
    }
  }, [debouncedQuery])

  // 무한 스크롤 api 요청 함수
  function loadMore() {
    return getMountains(debouncedQuery, mtInfoArray.slice(-1)[0].mountainId)
      .then((res) => {
        setMtInfoArray((mtInfoArray) => [...mtInfoArray, ...res.data.result])
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore })

  return (
    <div ref={infiniteRef} className={styles.container}>
      <MtList mtInfoArray={mtInfoArray} size="lg" />
      {isLoading && (
        <div className={styles.loading}>
          <Loading size="sm" />
        </div>
      )}
    </div>
  )
}

export default SearchMtPage

const mtInfoEx = [
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 2,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 3,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 4,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 15,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 16,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 17,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
]
