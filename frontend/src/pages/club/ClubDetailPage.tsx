import React, { useContext, useEffect } from 'react'

import styles from './ClubDetailPage.module.scss'

import { useNavigate, useParams } from 'react-router-dom'

import { useClubInfoQuery, useJoinClub } from 'apis/services/clubs'
import ClubMountain from 'components/club/ClubMountain'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import Button from 'components/common/Button'
import Loading from 'components/common/Loading'
import PageHeader from 'components/common/PageHeader'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import useUserQuery from 'hooks/useUserQuery'
import { ThemeContext } from 'styles/ThemeProvider'

function ClubDetailPage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const clubId = Number(useParams<string>().clubId)
  const { data: userInfo } = useUserQuery()

  const {
    isLoading,
    isError,
    data: clubInfo,
    isSuccess,
  } = useClubInfoQuery(clubId || 0)

  const {
    isLoading: isJoinClubLoading,
    isError: isJoinClubError,
    mutate: joinClub,
  } = useJoinClub(clubId)

  useEffect(() => {
    if (clubId === userInfo?.clubId) {
      navigate(`/club/${clubId}/main`)
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
            onClick={() => joinClub()}
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
      <ClubMountain zoom={2} assetInfo={clubInfo.assets} />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubDetailPage
