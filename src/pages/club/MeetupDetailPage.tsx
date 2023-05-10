import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MeetupDetailPage.module.scss'

import Button from 'components/common/Button'
import PageHeader from 'components/common/PageHeader'
import MeetupDetail from 'components/meetup/MeetupDetail'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import MeetupMembers from 'components/meetup/MeetupMembers'
import MeetupAlbum from 'components/meetup/MeetupAlbum'
import MeetupReviewList from 'components/meetup/MeetupReviewList'
import TextSendBar from 'components/common/TextSendBar'

import { meetupInfoDetail } from 'types/meetup.interface'

import { getMeetupDetail } from 'apis/services/meetup'
import { useQuery } from '@tanstack/react-query'

import { useParams } from 'react-router-dom'
import { useRecoilValue } from 'recoil'
import { userInfoState } from 'recoil/atoms'

function MeetupDetailPage() {
  const { theme } = useContext(ThemeContext)
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }
  const userInfo = useRecoilValue(userInfoState)

  const { data } = useQuery<meetupInfoDetail>(['meetup'], () =>
    getMeetupDetail(parseInt(clubId), parseInt(meetupId))
  )

  return data ? (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      {userInfo.memberId === data.meetupHostId ? (
        <Button text="삭제" color="red" size="xs" />
      ) : null}
      <PageHeader
        title={data?.meetupName}
        url={`/club/${clubId}/meetup`}
        color="primary"
      />
      <MeetupDetail
        mountain={data?.mountainName}
        date={data?.startAt.split(' ')[0]}
        time={data?.startAt.split(' ')[1]}
      />
      <div className={`page ${theme} ${styles.content}`}>
        <div className={styles.intro}>
          <MeetupIntroduction content={data?.description} />
        </div>
        <MeetupMembers />
        <MeetupAlbum photoInfo={data?.photoInfo} />
        <MeetupReviewList reviewInfo={data?.reviewInfo} />
      </div>
      <TextSendBar placeholder="후기를 입력해주세요" />
    </div>
  ) : (
    <div>Loading...</div>
  )
}

export default MeetupDetailPage
