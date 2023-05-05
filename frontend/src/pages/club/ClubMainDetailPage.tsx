import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainDetailPage.module.scss'

import Button from 'components/common/Button'

import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import SearchBar from 'components/common/SearchBar'


function ClubMainDetailPage() {
  const { theme } = useContext(ThemeContext)
  const [value, setValue] = useState('')

  return (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      <span className={styles.title}>산타마리아</span>
      <div className={styles.button}>
        <Button text="가입 신청" size="sm" color="primary" onClick={() => console.log("가입 신청 axios 연결")} />
      </div>
      <ClubRecordInfo
        participationRate="36.3"
        totalDuration="24"
        totalDistance={81}
        totalAlt={1580}
      />
      <div className={styles.intro}>
        <MeetupIntroduction content={'마리아~ 산타마리아'} />
      </div>
      <img src={clubmountain} className={styles.image} />
    </div>
  )
}

export default ClubMainDetailPage
