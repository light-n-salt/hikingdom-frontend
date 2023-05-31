import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MeetupDetailPage.module.scss'
import apiRequest from 'apis/axios'

import Button from 'components/common/Button'
import PageHeader from 'components/common/PageHeader'
import MeetupDetail from 'components/meetup/MeetupDetail'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import MeetupMembers from 'components/meetup/MeetupMembers'
import MeetupAlbum from 'components/meetup/MeetupAlbum'
import MeetupReviewList from 'components/meetup/MeetupReviewList'
import TextSendBar from 'components/common/TextSendBar'
import toast from 'components/common/Toast'
import Loading from 'components/common/Loading'

import {
  useMeetupDetailQuery,
  useMeetupReviewsQuery,
  deleteMeetup,
} from 'apis/services/meetup'
import { useMutation, useQueryClient } from '@tanstack/react-query'

import { useParams, useNavigate } from 'react-router-dom'

function MeetupDetailPage() {
  const { theme } = useContext(ThemeContext)
  const { meetupId, clubId } = useParams() as {
    meetupId: string
    clubId: string
  }
  const [content, setContent] = useState<string>('') // 후기 내용
  const queryClient = useQueryClient()
  const navigate = useNavigate()

  const { data: meetup } = useMeetupDetailQuery(clubId, meetupId)
  const { data: reviews } = useMeetupReviewsQuery(clubId, meetupId)

  const { mutate: writeReview } = useMutation(
    () =>
      apiRequest.post(`/clubs/${clubId}/meetups/${meetupId}/reviews`, {
        content,
      }),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['reviews'])
        toast.addMessage('success', '후기가 등록되었습니다')
        setContent('')
      },
    }
  )

  // 일정 삭제
  const onClickDeleteMeetup = useMutation(
    () => deleteMeetup(clubId || 0, parseInt(meetupId)),
    {
      onSuccess: () => {
        toast.addMessage('success', '일정이 삭제되었습니다')

        navigate(`/club/main`)
      },
      onError: (err: any) => {
        if (err.status === 400) {
          toast.addMessage('error', '완료된 일정은 삭제할 수 없습니다')
        }
      },
    }
  )

  return !meetup ? (
    <div>
      <Loading />
    </div>
  ) : (
    <div className={`page p-md ${theme} ${styles.page}`}>
      <PageHeader
        title={meetup?.meetupName}
        url={`/club/meetup`}
        color="primary"
        size="sm"
      />

      <MeetupDetail
        mountain={meetup?.mountainName}
        date={meetup?.startAt.split(' ')[0].replaceAll('-', '.').slice(-8)}
        time={meetup?.startAt.split(' ')[1].slice(0, 5)}
      />
      <div className={`page ${theme} ${styles.content}`}>
        <div className={styles.intro}>
          <MeetupIntroduction content={meetup?.description} />
        </div>
        <MeetupMembers />
        <MeetupAlbum join={meetup?.join} />
        {reviews && <MeetupReviewList reviewInfo={reviews} />}
      </div>
      {meetup?.join && (
        <TextSendBar
          placeholder="후기를 입력해주세요"
          content={content}
          setContent={setContent}
          onClick={() => writeReview()}
        />
      )}
      {/* <div className={styles.btn}>
        {userInfo?.memberId === meetup.meetupHostId ? (
          <Button
            text="삭제"
            color="red"
            size="xs"
            onClick={() => onClickDeleteMeetup.mutate()}
          />
        ) : null}
      </div> */}
    </div>
  )
}

export default MeetupDetailPage
