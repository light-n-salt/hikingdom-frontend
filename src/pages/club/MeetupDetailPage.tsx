import React, { useContext, useState, useEffect } from 'react'
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

import { meetupInfoDetail, MeetupReview } from 'types/meetup.interface'

import { getMeetupDetail, updateReview, getReviews } from 'apis/services/meetup'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'

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
  const [content, setContent] = useState<string>('') // 후기 내용
  const queryClient = useQueryClient()

  // 모임 정보
  const {
    data: meetup,
    isLoading,
    isError,
  } = useQuery<meetupInfoDetail>(['meetup'], () =>
    getMeetupDetail(parseInt(clubId), parseInt(meetupId))
  )

  // 후기 조회
  const { data: reviews } = useQuery<MeetupReview[]>(['reviews'], () =>
    getReviews(parseInt(clubId), parseInt(meetupId))
  )

  // 후기 등록
  const onClickUpdateReview = useMutation(
    () => updateReview(parseInt(clubId), parseInt(meetupId), content),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['reviews'])
        toast.addMessage('success', '후기가 등록되었습니다.')
        setContent('')
      },
    }
  )

  return isError || isLoading ? (
    <div>Loading...</div>
  ) : (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      {userInfo.memberId === meetup.meetupHostId ? (
        <Button text="삭제" color="red" size="xs" />
      ) : null}
      <PageHeader
        title={meetup?.meetupName}
        url="/club/meetup"
        color="primary"
      />
      <MeetupDetail
        mountain={meetup?.mountainName}
        date={meetup?.startAt.split(' ')[0]}
        time={meetup?.startAt.split(' ')[1]}
      />
      <div className={`page ${theme} ${styles.content}`}>
        <div className={styles.intro}>
          <MeetupIntroduction content={meetup?.description} />
        </div>
        <MeetupMembers />
        <MeetupAlbum photoInfo={meetup?.photoInfo} />
        {reviews && <MeetupReviewList reviewInfo={reviews} />}
      </div>
      <TextSendBar
        placeholder="후기를 입력해주세요"
        content={content}
        setContent={setContent}
        onClick={() => onClickUpdateReview.mutate()}
      />
    </div>
  )
}

export default MeetupDetailPage
