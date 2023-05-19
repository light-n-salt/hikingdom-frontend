import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { deleteClub } from 'apis/services/clubs'
import { useQuery } from '@tanstack/react-query'
import { ClubDetailInfo } from 'types/club.interface'
import Loading from 'components/common/Loading'
import SearchClubMt from 'components/club/SearchClubMt'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import useUserQuery from 'hooks/useUserQuery'

function ClubMainPage() {
  const { theme } = useContext(ThemeContext)

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const { data: clubInfo } = useQuery<ClubDetailInfo>(
    ['ClubDetailInfo', clubId],
    () => getClubInfo(clubId || 0),
    {
      enabled: !!clubId,
    }
  )

  return clubInfo ? (
    <>
      <div className={`page-gradation upside p-md ${theme} ${styles.page}`}>
        <ClubRecordInfo
          totalMember={clubInfo.totalMember}
          totalMountainCount={clubInfo.totalMountainCount}
          totalMeetupCount={clubInfo.totalMeetupCount}
          totalAssetCount={clubInfo.totalAssetCount}
        />
        <div className={styles.intro}>
          <MeetupIntroduction content={clubInfo.description} />
        </div>
        <SearchClubMt assetInfo={clubInfo.assets} />
      </div>
    </>
  ) : (
    <Loading />
  )
}

export default ClubMainPage
