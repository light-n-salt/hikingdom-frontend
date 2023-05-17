import React, { useContext, useEffect } from 'react'
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

  const assetArray = getPosition(assetInfo).arr

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
      <ClubMountain zoom={3} assetInfo={assetArray} />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubDetailPage

const assetInfo = [
  {
    mountainName: '',
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
