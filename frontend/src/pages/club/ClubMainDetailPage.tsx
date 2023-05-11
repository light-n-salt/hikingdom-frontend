import React, { useContext, useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainDetailPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { postJoinClub } from 'apis/services/clubs'
import { ClubDetailInfo } from 'types/club.interface'

import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import Loading from 'components/common/Loading'
import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import { useQuery } from '@tanstack/react-query'

function ClubMainDetailPage() {
  const { theme } = useContext(ThemeContext)

  const clubId = useParams<string>().clubId

  const { data: clubInfo } = useQuery<ClubDetailInfo>(
    ['ClubDetailInfo', { clubId: clubId }],
    () => getClubInfo(Number(clubId))
  )

  function onClickJoinClub() {
    postJoinClub(Number(clubId))
      .then(() => toast.addMessage('success', '가입이 신청되었습니다.'))
      .catch((err) => toast.addMessage('error', `${err.data.message}`))
  }

  return clubInfo ? (
    <div className={`page-gradation upside p-sm ${theme} ${styles.page}`}>
      <h1 className={styles.title}>{clubInfo.clubName}</h1>
      <div className={styles.button}>
        <Button
          text="가입 신청"
          size="sm"
          color="primary"
          onClick={onClickJoinClub}
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
    <Loading />
  )
}

export default ClubMainDetailPage
