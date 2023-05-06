import React, { useContext, useEffect, useRef, useState } from 'react'
import styles from './RankPage.module.scss'
import { getRanking } from 'apis/services/clubs'
import Dropdown from 'components/common/Dropdown'
import RankList from 'components/common/RankList'
import RankHeader from 'components/rank/RankHeader'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'
import Loading from 'components/common/Loading'

// 드롭다운 SelectBox에 넘길 옵션 배열
const filterOptions = [
  { label: '종합랭킹 순', value: '' },
  { label: '참여도 순', value: 'participation' },
  { label: '거리 순', value: 'distance' },
  { label: '시간 순', value: 'time' },
]

function RankPage() {
  const { theme } = useContext(ThemeContext)
  const [filter, setFilter] = useState('') // 선택된 필터 옵션 value
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>([]) // 클럽정보 배열
  const [isEnd, setIsEnd] = useState(false) // 무한스크롤 마지막 정보 여부
  const infiniteRef = useRef(null) // 무한 스크롤 동작시킬 useRef

  // 필터 옵션이나 쿼리가 변할 때마다, 클럽 랭킹 정보 api 요청
  useEffect(() => {
    getRanking(filter).then((res) => {
      setClubInfoArray(res.data.result)
      // setIsEnd(!res.data.result.hasNext)
    })
  }, [filter])

  // 무한 스크롤 api 요청 함수
  function loadMore() {
    return getRanking(filter, clubInfoArray.slice(-1)[0].clubId)
      .then((res) => {
        setClubInfoArray((clubInfoArray) => [
          ...clubInfoArray,
          ...res.data.result,
        ])
        // setIsEnd(!res.data.result.hasNext)
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore, isEnd })

  return (
    <div className={`page ${theme}`}>
      <RankHeader />
      <div className={styles.select}>
        <Dropdown options={filterOptions} setValue={setFilter} />
      </div>
      <div ref={infiniteRef} className={styles.clubs}>
        <RankList clubInfoArray={clubInfoArray} />
        {isLoading && (
          <div className={styles.loading}>
            <Loading size="sm" />
          </div>
        )}
      </div>
    </div>
  )
}

export default RankPage
