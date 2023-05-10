import React, { useRef, useMemo } from 'react'
import styles from './PastMeetupList.module.scss'
import PastMeetupItem from './PastMeetupItem'
import Loading from 'components/common/Loading'
import { useParams } from 'react-router-dom'
import { UserHiking } from 'types/user.interface'

import { getPastMeetups } from 'apis/services/users'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { useInfiniteQuery } from '@tanstack/react-query'

type InfiniteMeetupInfo = {
  content: UserHiking[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

export default function PastMeetupList() {
  const { nickname } = useParams() as { nickname: string }
  const infiniteRef = useRef<HTMLDivElement>(null)

  const { data, isLoading, fetchNextPage, hasNextPage } =
    useInfiniteQuery<InfiniteMeetupInfo>({
      queryKey: ['pastMeetup'],
      queryFn: ({ pageParam = null }) => {
        return getPastMeetups(nickname, pageParam)
      },
      getNextPageParam: (lastPage) => {
        return lastPage.hasNext
          ? lastPage.content.slice(-1)[0].meetupId
          : undefined
      },
    })

  const records = useMemo(() => {
    if (!data) return []
    return data.pages.flatMap((page) => page.content)
  }, [data])

  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return (
    <div ref={infiniteRef} className={styles.scroll}>
      <div className={styles.list}>
        {records?.map((hiking) => (
          <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
        ))}
      </div>
      {isLoading && <Loading size="sm" />}
    </div>
  )
}
