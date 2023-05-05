import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainPage.module.scss'

import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import SearchBar from 'components/common/SearchBar'

function ClubMainPage() {
  const { theme } = useContext(ThemeContext)
  const [value, setValue] = useState('')

  return (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      <ClubRecordInfo
        participationRate="36.3"
        totalDuration="24"
        totalDistance={81}
        totalAlt={1580}
      />
      <div className={styles.intro}>
        <MeetupIntroduction content={'마리아~ 산타마리아'} />
        <SearchBar
          value={value}
          placeholder="등산했던 산을 검색해보세요"
          onChangeText={setValue}
        />
      </div>
      <img src={clubmountain} className={styles.image} />
    </div>
  )
}

export default ClubMainPage
