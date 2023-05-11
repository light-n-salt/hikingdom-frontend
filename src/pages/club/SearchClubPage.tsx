import React, { useContext, useEffect, useRef, useState } from 'react'
import styles from './SearchClubPage.module.scss'
import { getClubs } from 'apis/services/clubs'
import RankList from 'components/common/RankList'
import InputDropdown from 'components/common/InputDropdown'
import useDebounce from 'hooks/useDebounce'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'
import Loading from 'components/common/Loading'

// 서치바의 드롭다운  SelectBox에 넘길 옵션 배열
const filterOptions = [
  { label: '모임이름', value: 'name' },
  // { label: '지역', value: 'participation' },
]

function SearchClubPage() {
  const { theme } = useContext(ThemeContext)

  const [query, setQuery] = useState('') // input 태그의 검색 쿼리
  const [filter, setFilter] = useState('') // 선택된 필터
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>(clubInfoEx) // 클럽 정보 배열
  const [isEnd, setIsEnd] = useState(false) // 무한스크롤 마지막 정보 여부
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한스크롤 ref 요소

  const debouncedQuery = useDebounce(query) // debounced query

  // 서치바에 input태그 onChange 마다 query 업데이트하는 함수
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setQuery(event.target.value)
  }

  // 필터 옵션이나 쿼리가 변할 때마다, 클럽 정보 api 요청
  useEffect(() => {
    getClubs(filter, query).then((res) => {
      setClubInfoArray(res.data.result.content)
      setIsEnd(!res.data.result.hasNext)
      if (infiniteRef.current) {
        infiniteRef.current.scrollTo({
          top: 0,
          left: 0,
          behavior: 'smooth',
        })
      }
    })
  }, [filter, debouncedQuery])

  // 무한 스크롤 시 동작할, api 요청 함수
  function loadMore() {
    return getClubs(filter, query, clubInfoArray.slice(-1)[0].clubId)
      .then((res) => {
        setClubInfoArray((clubInfoArray) => [
          ...clubInfoArray,
          ...res.data.result,
        ])
        setIsEnd(!res.data.result.hasNext)
      })
      .catch(() => {})
  }

  // 무한 스크롤 커스텀 훅
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore, isEnd })

  return (
    <div className={`page ${theme} p-md ${styles.container}`}>
      <div className={styles.search}>
        <InputDropdown
          value={query}
          placeholder="소모임을 검색하세요"
          options={filterOptions}
          setFilter={setFilter}
          onChange={onChange}
        />
      </div>
      <div ref={infiniteRef} className={styles.clubs}>
        <RankList clubInfoArray={clubInfoArray} size="lg" />
        {isLoading && (
          <div className={styles.loading}>
            <Loading size="sm" />
          </div>
        )}
      </div>
    </div>
  )
}

export default SearchClubPage

const clubInfoEx: ClubInfo[] = [
  {
    clubId: 199,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 15,
  },
  {
    clubId: 4,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 2,
  },
  {
    clubId: 137,
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
  {
    clubId: 16,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
  {
    clubId: 17,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
]
