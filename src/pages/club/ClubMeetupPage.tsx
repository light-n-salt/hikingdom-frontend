import React from 'react'
import styles from './ClubMeetupPage.module.scss'
import Calendar from 'components/club/Calendar'
import MeetupList from 'components/club/MeetupList'

function ClubMeetupPage() {
  return (
    <div className={styles.container}>
      <Calendar />
      <MeetupList />
    </div>
  )
}

export default ClubMeetupPage
