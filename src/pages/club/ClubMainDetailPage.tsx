import React, { useContext, useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainDetailPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { ClubDetailInfo } from 'types/club.interface'

import Button from 'components/common/Button'
import SearchBar from 'components/common/SearchBar'
import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'

function ClubMainDetailPage() {
  const { theme } = useContext(ThemeContext)
  const [value, setValue] = useState('')
  const [clubInfo, setClubInfo] = useState<ClubDetailInfo>()

  const clubId = useParams<string>().clubId

  useEffect(() => {
    getClubInfo(Number(clubId)).then((res) => setClubInfo(res.data.result))
  }, [])

  return clubInfo ? (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      <span className={styles.title}>{clubInfo.clubName}</span>
      <div className={styles.button}>
        <Button
          text="가입 신청"
          size="sm"
          color="primary"
          onClick={() => console.log('가입 신청 axios 연결')}
        />
      </div>
      <ClubRecordInfo
        participationRate={clubInfo.participationRate}
        totalDuration={clubInfo.totalDuration}
        totalDistance={clubInfo.totalDistance}
        totalAlt={clubInfo.totalAlt}
      />
      <div className={styles.intro}>
        <MeetupIntroduction content={clubInfo.description} />
      </div>
      <img src={clubmountain} className={styles.image} />
    </div>
  ) : (
    <div>Loading....</div>
  )
}

export default ClubMainDetailPage
