import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import IconButton from 'components/common/IconButton'

import bell from 'assets/images/bell.png'

function ProfilePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const userRecord = {
    totalHikingCount: 10,
    totalMountainCount: 30,
    totalDuration: '30:23',
    totalDistance: 34,
    totalAlt: 34,
  }

  const userHikingList = [
    {
      hikingRecordId: 0,
      mountainName: '도봉산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: true,
      meetupId: 3,
      meetupName: '등산 가즈아',
    },

    {
      hikingRecordId: 1,
      mountainName: '도봉산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: false,
      meetupId: null,
      meetupName: null,
    },

    {
      hikingRecordId: 2,
      mountainName: '수락산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: false,
      meetupId: null,
      meetupName: null,
    },
  ]

  return (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
      <UserProfile />
      <div className={styles.title}>등산기록</div>
      <PastMeetupList />
    </div>
  )
}

export default ProfilePage
