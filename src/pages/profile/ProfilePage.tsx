import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate, useParams } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import IconButton from 'components/common/IconButton'
import Loading from 'components/common/Loading'
import bell from 'assets/images/bell.png'

import { UserProfileInfo } from 'types/user.interface'

import { getProfile } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const { data: userInfo } = useUserQuery() // 유저 정보
  const { nickname } = useParams() as { nickname: string }
  const { data, isLoading, isError } = useQuery<UserProfileInfo>(
    ['userHiking'],
    // ?? : 왼쪽 피연산자가 null 또는 undefined인 경우에 오른쪽 피연산자를 반환
    () => getProfile(nickname, 5),
    { enabled: !!userInfo }
  )

  return !userInfo || isError || isLoading ? (
    <Loading size="sm" />
  ) : (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <UserProfile
        profileUrl={userInfo?.profileUrl}
        nickname={userInfo?.nickname}
        email={userInfo?.email}
        level={userInfo?.level}
        totalAlt={data?.totalAlt}
        totalDistance={data?.totalDistance}
        totalDuration={data?.totalDuration}
        totalHikingCount={data?.totalHikingCount}
        totalMountainCount={data?.totalMountainCount}
      />
      <div className={styles.title}>등산기록</div>
      <PastMeetupList />
      <div className={styles.alarm}>
        <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
      </div>
    </div>
  )
}

export default ProfilePage
