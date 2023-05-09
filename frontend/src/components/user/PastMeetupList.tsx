import React from 'react'
import styles from './PastMeetupList.module.scss'
import PastMeetupItem from './PastMeetupItem'
import { UserHiking } from 'types/user.interface'

type PastMeetupProps = {
  hikingRecords: UserHiking[]
}

export default function PastMeetupList({ hikingRecords }: PastMeetupProps) {
  return hikingRecords.length ? (
    <div className={`${styles['meetup-list']}`}>
      {hikingRecords.map((hiking) => (
        <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
      ))}
    </div>
  ) : (
    <div>기록이 없습니다</div>
  )
}
