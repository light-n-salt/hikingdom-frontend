import React from 'react'
import styles from './MeetupList.module.scss'
import MeetupItem from 'components/club/MeetupItem'
import { MeetupInfo } from 'types/meetup.interface'

type MeetupListProps = {
  meetupInfoArray: MeetupInfo[]
}

function MeetupList({ meetupInfoArray }: MeetupListProps) {
  return (
    <div className={styles.container}>
      {meetupInfoArray.map((meetupInfo) => (
        <MeetupItem key={meetupInfo.meetupId} meetupInfo={meetupInfo} />
      ))}
    </div>
  )
}

export default MeetupList
