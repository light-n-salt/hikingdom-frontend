import React, { useContext } from 'react'

import styles from './ClubMainPage.module.scss'

import { useClubInfoQuery } from 'apis/services/clubs'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import SearchClubMt from 'components/club/SearchClubMt'
import Loading from 'components/common/Loading'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import useUserQuery from 'hooks/useUserQuery'
import { ThemeContext } from 'styles/ThemeProvider'

function ClubMainPage() {
  const { theme } = useContext(ThemeContext)

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const {
    isLoading,
    isError,
    data: clubInfo,
    isSuccess,
  } = useClubInfoQuery(clubId || 0)

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
