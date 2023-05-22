import React, { useContext, useEffect, useMemo } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubDetailPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { postJoinClub } from 'apis/services/clubs'
import { ClubDetailInfo } from 'types/club.interface'
import useUserQuery from 'hooks/useUserQuery'
import { useQuery } from '@tanstack/react-query'
import { getPosition } from 'utils/getPosition'

import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import Loading from 'components/common/Loading'
import PageHeader from 'components/common/PageHeader'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import ClubMountain from 'components/club/ClubMountain'

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
      .then(() => toast.addMessage('success', '가입신청이 완료되었습니다'))
      .catch((err) => toast.addMessage('error', `${err.data.message}`))
  }

  useEffect(() => {
    if (clubId === userInfo?.clubId) {
      navigate('/club/main')
    }
  }, [userInfo])

  return clubInfo && userInfo ? (
    <div className={`page-gradation upside p-sm ${theme} ${styles.page}`}>
      <PageHeader title={clubInfo.clubName} color="primary" />
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
        totalMember={clubInfo.totalMember}
        totalMountainCount={clubInfo.totalMountainCount}
        totalMeetupCount={clubInfo.totalMeetupCount}
        totalAssetCount={clubInfo.totalAssetCount}
      />
      <div className={styles.intro}>
        <MeetupIntroduction content={clubInfo.description} />
      </div>
      <ClubMountain zoom={3} assetInfo={clubInfo.assets} />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubDetailPage
