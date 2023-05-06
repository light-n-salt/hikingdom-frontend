import React, { useContext, useEffect, useRef, useState } from 'react'
import styles from './SearchClubPage.module.scss'
import { getClubs } from 'apis/services/clubs'
import RankList from 'components/common/RankList'
import SearchBarDropdown from 'components/common/SearchBarDropdown'
import useDebounce from 'hooks/useDebounce'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'

// 서치바의 드롭다운  SelectBox에 넘길 옵션 배열
const filterOptions = [
  { label: '전체', value: '' },
  { label: '모임이름', value: 'name' },
  { label: '지역', value: 'participation' },
]

function SearchClubPage() {
  const { theme } = useContext(ThemeContext)

  const [query, setQuery] = useState('') // input 태그의 검색 쿼리
  const [filter, setFilter] = useState('') // 선택된 필터
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>([]) // 클럽 정보 배열
  const infiniteRef = useRef(null) // 무한스크롤 ref 요소

  const debouncedQuery = useDebounce(query) // debounced query

  // 서치바에 input태그 onChange 마다 query 업데이트하는 함수
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setQuery(event.target.value)
  }

  // 필터 옵션이나 쿼리가 변할 때마다, 클럽 정보 api 요청
  useEffect(() => {
    getClubs(filter, query).then((res) => {})
  }, [filter, debouncedQuery])

  // 무한 스크롤 시 동작할, api 요청 함수
  function loadMore() {
    return getClubs(filter, query, clubInfoArray.slice(-1)[0].clubId)
      .then((res) => {
        setClubInfoArray((clubInfoArray) => [
          ...clubInfoArray,
          ...res.data.result,
        ])
      })
      .catch(() => {})
  }

  // 무한 스크롤 커스텀 훅
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore })

  return (
    <div className={`page ${theme} p-md ${styles.container}`}>
      <div className={styles.search}>
        <SearchBarDropdown
          value={query}
          placeholder="소모임을 검색하세요"
          options={filterOptions}
          setFilter={setFilter}
          onChange={onChange}
        />
      </div>
      <div ref={infiniteRef} className={styles.clubs}>
        <RankList clubInfoArray={clubInfoArray} size="lg" />
      </div>
    </div>
  )
}

export default SearchClubPage

const clubInfoArray: ClubInfo[] = [
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 15,
  },
  {
    clubId: 3,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 2,
  },
  {
    clubId: 13,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 15,
  },
  {
    clubId: 3,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 2,
  },
  {
    clubId: 13,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
]
