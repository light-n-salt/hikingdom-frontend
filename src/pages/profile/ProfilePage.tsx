import React, { useContext, useEffect, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import IconButton from 'components/common/IconButton'

import bell from 'assets/images/bell.png'

import { UserProfileInfo, User } from 'types/user.interface'

import { getProfile, getMember } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const { data: user } = useQuery<User>(['user'], getMember, {
    cacheTime: Infinity,
  })

  // Todo: nickname 변수 변경
  const { data } = useQuery<UserProfileInfo>(
    ['profile'],
    () => getProfile('yzii'),
    { enabled: !!user }
  )

  return user && data ? (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <UserProfile
        profileUrl={user.profileUrl}
        nickname={user.nickname}
        email={user.email}
        level={0}
        totalAlt={data.totalAlt}
        totalDistance={data.totalDistance}
        totalDuration={data.totalDuration}
        totalHikingCount={data.totalHikingCount}
        totalMountainCount={data.totalMountainCount}
      />
      <div className={styles.title}>등산기록</div>
      <PastMeetupList hikingRecords={data.hikingRecords} />
      <div className={styles.alarm}>
        <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
      </div>
    </div>
  ) : (
    <div>Loading ...</div>
  )
}

export default ProfilePage
