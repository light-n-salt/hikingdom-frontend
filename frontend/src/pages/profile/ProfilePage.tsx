import React, { useContext, useEffect, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import IconButton from 'components/common/IconButton'

import bell from 'assets/images/bell.png'

import { UserProfileInfo } from 'types/user.interface'

import { getProfile } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'

import { useRecoilValue } from 'recoil'
import { userInfoState } from 'recoil/atoms'

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const userInfo = useRecoilValue(userInfoState)

  const { data } = useQuery<UserProfileInfo>(['profile'], () =>
    getProfile(userInfo.nickname)
  )

  return userInfo && data ? (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <UserProfile
        profileUrl={userInfo.profileUrl}
        nickname={userInfo.nickname}
        email={userInfo.email}
        level={userInfo.level}
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
