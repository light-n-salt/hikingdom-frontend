import React, { useContext, useEffect, useRef, useState, useMemo } from 'react'
import styles from './RankPage.module.scss'
import { useQuery, useInfiniteQuery } from '@tanstack/react-query'
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
  hasPrev: boolean
  numberOfElements: number
  pageSize: number
}

// 드롭다운 SelectBox에 넘길 옵션 배열
const filterOptions = [
  { label: '종합랭킹 순', value: '' },
  { label: '참여도 순', value: 'participation' },
  { label: '거리 순', value: 'distance' },
  { label: '시간 순', value: 'time' },
]

function RankPage() {
  const { theme } = useContext(ThemeContext)
  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const [filter, setFilter] = useState('') // 선택된 필터 옵션 value
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>([]) // 클럽정보 배열
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 동작시킬 useRef

  // 필터 옵션이나 쿼리가 변할 때마다, 클럽 랭킹 정보 api 요청
  const { isLoading, isError, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useInfiniteQuery<InfiniteClubInfo>({
      queryKey: ['rank', filter],
      queryFn: ({ pageParam = null }) => getRanking(filter, pageParam),
      getNextPageParam: (lastPage) => {
        return lastPage.hasNext
          ? lastPage.content.slice(-1)[0].clubId
          : undefined
      },
      onSuccess: (res) => {
        console.log(res)
        if (res.pageParams.length === 1) {
          setClubInfoArray(res.pages.slice(-1)[0].content)
        } else {
          setClubInfoArray((clubs) => [
            ...clubs,
            ...res.pages.slice(-1)[0].content,
          ])
        }
      },
      cacheTime: queryTime,
      staleTime: queryTime,
    })

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
