import React, { useContext, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubDetailPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { postJoinClub } from 'apis/services/clubs'
import { ClubDetailInfo } from 'types/club.interface'
import useUserQuery from 'hooks/useUserQuery'
import { useQuery } from '@tanstack/react-query'

import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import Loading from 'components/common/Loading'
import PageHeader from 'components/common/PageHeader'
import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import ClubMoutain from 'components/club/ClubMoutain'

function ClubDetailPage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const clubId = Number(useParams<string>().clubId)
  const { data: userInfo } = useUserQuery()

  const { data: clubInfo } = useQuery<ClubDetailInfo>(
    ['ClubDetailInfo', { clubId: clubId }],
    () => getClubInfo(clubId)
  )

  function onClickJoinClub() {
    postJoinClub(clubId)
      .then(() => toast.addMessage('success', '가입이 신청되었습니다.'))
      .catch((err) => toast.addMessage('error', `${err.data.message}`))
  }

  useEffect(() => {
    if (clubId === userInfo?.clubId) {
      navigate('/club/main')
    }
  }, [userInfo])

  return clubInfo && userInfo ? (
    <div className={`page-gradation upside p-sm ${theme} ${styles.page}`}>
      <PageHeader title={clubInfo.clubName} url="" color="primary" />
      {/* <h1 className={styles.title}>{clubInfo.clubName}</h1> */}
      <div className={styles.button}>
        {!userInfo.clubId && (
          <Button
            text="가입 신청"
            size="sm"
            color="primary"
            onClick={onClickJoinClub}
          />
        )}
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
      {/* <img src={clubmountain} className={styles.image} /> */}
      <ClubMoutain zoom={3} />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubDetailPage
