import React, { useContext } from 'react'
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

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery() // 유저 정보
  const { nickname } = useParams() as { nickname: string }
  const { data, isLoading, isError } = useQuery<UserProfileInfo>(
    ['userProfile', nickname],
    () => getProfile(nickname, 5),
    { enabled: !!userInfo }
  )

  return !userInfo || isError || isLoading ? (
    <Loading size="sm" />
  ) : (
    <div className={`page p-md ${theme} ${styles.profile}`}>
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
        <PastMeetupList />
      </div>
    </div>
  )
}

export default ProfilePage
