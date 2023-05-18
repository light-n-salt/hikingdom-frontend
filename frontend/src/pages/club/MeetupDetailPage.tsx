import React, { useContext, useState } from 'react'
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
import toast from 'components/common/Toast'
import Loading from 'components/common/Loading'
import { meetupInfoDetail, MeetupReview } from 'types/meetup.interface'

import {
  getMeetupDetail,
  updateReview,
  getReviews,
  deleteMeetup,
} from 'apis/services/meetup'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'

import { useParams, useNavigate } from 'react-router-dom'
import useUserQuery from 'hooks/useUserQuery'

function MeetupDetailPage() {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId
  const { meetupId } = useParams() as {
    meetupId: string
  }

  const [content, setContent] = useState<string>('') // 후기 내용
  const queryClient = useQueryClient()
  const navigate = useNavigate()
  // 모임 정보
  const { data: meetup } = useQuery<meetupInfoDetail>(['meetup'], () =>
    getMeetupDetail(clubId || 0, parseInt(meetupId))
  )

  // 후기 조회
  const { data: reviews } = useQuery<MeetupReview[]>(['reviews'], () =>
    getReviews(clubId || 0, parseInt(meetupId))
  )

  // 후기 등록
  const onClickUpdateReview = useMutation(
    () => updateReview(clubId || 0, parseInt(meetupId), content),
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

  return !userInfo || !meetup ? (
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
          onClick={() => onClickUpdateReview.mutate()}
        />
      )}
      <div className={styles.btn}>
        {userInfo?.memberId === meetup.meetupHostId ? (
          <Button
            text="삭제"
            color="red"
            size="xs"
            onClick={() => onClickDeleteMeetup.mutate()}
          />
        ) : null}
      </div>
    </div>
  )
}

export default MeetupDetailPage
