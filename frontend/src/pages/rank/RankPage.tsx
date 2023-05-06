import React, { useContext, useEffect, useState, useRef } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './RankPage.module.scss'
import trophy from 'assets/images/trophy.png'
import { BiSearch } from 'react-icons/bi'
import IconButton from 'components/common/IconButton'
import { useNavigate } from 'react-router-dom'
import SelectBox from 'components/common/SelectBox'
import Dropdown from 'components/common/Dropdown'
import RankList from 'components/common/RankList'
import { getRanking } from 'apis/services/clubs'
import RankHeader from 'components/rank/RankHeader'
import { ClubInfo } from 'types/club.interface'
import useInfiniteScroll from 'hooks/useInfiniteScroll'

const filterOptions = [
  { label: '종합랭킹 순', value: '' },
  { label: '참여도 순', value: 'participation' },
  { label: '거리 순', value: 'distance' },
  { label: '시간 순', value: 'time' },
]

function RankPage() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)
  const [filter, setFilter] = useState('')
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>([])
  const infiniteRef = useRef(null)

  useEffect(() => {
    getRanking(filter).then((res) => {
      setClubInfoArray(res.data.result)
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
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore })

  return (
    <div className={`page ${theme}`}>
      <RankHeader />
      <div className={styles.select}>
        <Dropdown options={filterOptions} setValue={setFilter} />
      </div>
      <div ref={infiniteRef} className={styles.clubs}>
        <RankList clubInfoArray={clubInfoArray} />
      </div>
    </div>
  )
}

export default RankPage
