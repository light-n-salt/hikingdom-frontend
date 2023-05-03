import React from 'react'
import styles from './PastMeetupList.module.scss'

import PastMeetupItem from './PastMeetupItem'

import { UserHiking } from 'types/user.interface'

// Todo: API 연결
export default function PastMeetupList() {
  const userHikingList: UserHiking[] = [
    {
      hikingRecordId: 0,
      mountainName: '도봉산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: true,
      scheduleId: 3,
      scheduleName: '등산 가즈아',
    },

    {
      hikingRecordId: 1,
      mountainName: '도봉산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: false,
      scheduleId: null,
      scheduleName: null,
    },

    {
      hikingRecordId: 2,
      mountainName: '수락산',
      startAt: 'YYYY.MM.DD HH:mm:ss',
      totalDuration: '23:00',
      totalDistance: 402,
      maxAlt: 203,
      isGroup: false,
      scheduleId: null,
      scheduleName: null,
    },
  ]

  return (
    <div className={`${styles['meetup-list']}`}>
      {userHikingList.map((hiking) => (
        <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
      ))}
    </div>
  )
}
