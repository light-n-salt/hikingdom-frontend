import React, { useContext, useRef, useState, useMemo } from 'react'
import styles from './RankPage.module.scss'
import { useInfiniteQuery } from '@tanstack/react-query'
import { getRanking } from 'apis/services/clubs'
import Dropdown from 'components/common/Dropdown'
import RankList from 'components/common/RankList'
import RankHeader from 'components/rank/RankHeader'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'
import Loading from 'components/common/Loading'
import { untilMidnight } from 'utils/untilMidnight'

type InfiniteClubInfo = {
  content: ClubInfo[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

// 드롭다운 SelectBox에 넘길 옵션 배열
const filterOptions = [
  { label: '종합랭킹 순', value: '' },
  { label: '완등 산 순', value: 'mountain' },
  { label: '에셋 순', value: 'asset' },
  { label: '멤버 순', value: 'member' },
]

function RankPage() {
  const { theme } = useContext(ThemeContext)
  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const [filter, setFilter] = useState('') // 선택된 필터 옵션 value
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 동작시킬 useRef

  // 필터 옵션이나 쿼리가 변할 때마다, 클럽 랭킹 정보 api 요청
  const {
    data,
    isLoading,
    isError,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<InfiniteClubInfo>({
    queryKey: ['rank', filter],
    queryFn: ({ pageParam = null }) => getRanking(filter, pageParam),
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext ? lastPage.content.slice(-1)[0].clubId : undefined
    },
    cacheTime: queryTime,
    staleTime: queryTime,
  })

  // infiniteQuery로 받은 배열을 가공
  const clubInfoArray = useMemo(() => {
    if (!data) return []
    return data.pages.flatMap((page: InfiniteClubInfo) => page.content)
  }, [data])

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return (
    <div className={`page ${theme}`}>
      <RankHeader />
      <div className={styles.select}>
        <Dropdown options={filterOptions} setValue={setFilter} />
      </div>
      <div ref={infiniteRef} className={`page p-md ${styles.clubs}`}>
        {isLoading || isError ? (
          <Loading />
        ) : (
          <>
            <RankList clubInfoArray={clubInfoArray} />
            {isFetchingNextPage && (
              <div className={styles.loading}>
                <Loading size="sm" />
              </div>
            )}
          </>
        )}
      </div>
    </div>
  )
}

export default RankPage
