import React, { useContext, useRef, useMemo } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useParams } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import Loading from 'components/common/Loading'

import { UserProfileInfo } from 'types/user.interface'

import { getProfile } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'
import { UserHiking } from 'types/user.interface'

import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { useInfiniteQuery } from '@tanstack/react-query'
import { getPastMeetups } from 'apis/services/users'

type InfiniteMeetupInfo = {
  content: UserHiking[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery() // 유저 정보
  const { nickname } = useParams() as { nickname: string }
  const infiniteRef = useRef<HTMLDivElement>(null)

  const { data, isLoading, isError } = useQuery<UserProfileInfo>(
    ['userProfile', nickname],
    () => getProfile(nickname, 5),
    { enabled: !!userInfo }
  )

  const {
    data: pastRecord,
    isLoading: pastLoading,
    isFetchingNextPage,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery<InfiniteMeetupInfo>({
    queryKey: ['pastMeetup'],
    queryFn: ({ pageParam = null }) => {
      return getPastMeetups(nickname, pageParam)
    },
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext
        ? lastPage.content.slice(-1)[0].hikingRecordId
        : undefined
    },
  })

  const records = useMemo(() => {
    if (!pastRecord) return []
    return pastRecord.pages.flatMap((page) => page.content)
  }, [pastRecord])

  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return !userInfo || isError || isLoading ? (
    <Loading size="sm" />
  ) : (
    <div className={`page p-md ${theme} ${styles.profile}`} ref={infiniteRef}>
      <UserProfile
        profileUrl={data?.profileUrl}
        nickname={data?.nickname}
        email={data?.email}
        level={data?.level}
        totalAlt={data?.totalAlt}
        totalDistance={data?.totalDistance}
        totalDuration={data?.totalDuration}
        totalHikingCount={data?.totalHikingCount}
        totalMountainCount={data?.totalMountainCount}
        unreadNotificationCount={data?.unreadNotificationCount}
      />
      <div className={styles.meetups}>
        <div className={styles.title}>등산기록</div>
        {pastLoading ? (
          <Loading />
        ) : records.length ? (
          <PastMeetupList records={records} />
        ) : (
          <p className={styles.blank}>일정 기록이 없습니다</p>
        )}
        {isFetchingNextPage && <Loading />}
      </div>
    </div>
  )
}

export default ProfilePage
