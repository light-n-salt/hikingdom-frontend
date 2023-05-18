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
        <SearchClubMt assetInfo={assetInfo} />
      </div>
    </>
  ) : (
    <Loading />
  )
}

export default ClubMainPage

const assetInfo = [
  {
    mountainName: '전체',
    meetupId: 0,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/main.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower5.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower1.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리왕산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower2.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower4.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '관악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower6.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리왕산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower7.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower8.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower9.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '관악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower10.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '도봉산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower11.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '대둔산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower12.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '덕숭산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower13.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower14.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '도봉산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower15.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리왕산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower16.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '명성산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower17.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower18.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower19.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower20.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리왕산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower21.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower22.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower23.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower24.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower25.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower26.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower27.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower28.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower29.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower30.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower1.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower5.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '가리산',
    meetupId: 1,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower2.gltf',
    row: 0,
    column: 0,
  },
  {
    mountainName: '감악산',
    meetupId: 3,
    assetUrl:
      'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower10.gltf',
    row: 0,
    column: 0,
  },
]
