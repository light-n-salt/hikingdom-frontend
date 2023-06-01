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
import Modal from 'components/common/Modal'
import ConfirmModal from 'components/club/ConfirmModal'
import Loading from 'components/common/Loading'
import {
  useMeetupDetailQuery,
  useMeetupReviewsQuery,
  useDeleteMeetup,
  usePostReview,
} from 'apis/services/meetup'
import { useParams } from 'react-router-dom'
import useUserQuery from 'hooks/useUserQuery'
import useRedirect from 'hooks/useRedirect'

function MeetupDetailPage() {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()

  const { meetupId, clubId } = useParams() as {
    meetupId: string
    clubId: string
  }

  const { arg1: parsedClubId, arg2: parsedMeetupId } = useRedirect(
    clubId,
    meetupId
  )

  const [isDeleteOpen, setIsDeleteOpen] = useState(false) // 삭제 모달
  const [content, setContent] = useState<string>('') // 후기 내용

  // 모임 조회
  const {
    isLoading: isMeetupLoading,
    isError: isMeetupError,
    data: meetup,
  } = useMeetupDetailQuery(parsedClubId, parsedMeetupId)

  // 후기 조회
  const {
    isLoading: isReviewsLoading,
    isError: isReviewsError,
    data: reviews,
  } = useMeetupReviewsQuery(parsedClubId, parsedMeetupId)

  // 후기 등록
  const {
    isLoading: isPostReviewLoading,
    isError: isPostReviewError,
    mutateAsync: postReview,
  } = usePostReview(parsedClubId, parsedMeetupId, content)

  const onClickPostReview = () => {
    postReview().then(() => {
      setContent('')
    })
  }

  // 일정 삭제
  const onClickDeleteMeetup = () => {
    deleteMeetup().catch(() => setIsDeleteOpen(false))
  }

  const {
    isLoading: isDeleteMeetupLoading,
    isError: isDeleteMeetupError,
    mutateAsync: deleteMeetup,
  } = useDeleteMeetup(parsedClubId, parsedMeetupId)

  if (
    isMeetupLoading ||
    isReviewsLoading ||
    isPostReviewLoading ||
    isDeleteMeetupLoading
  ) {
    return <Loading />
  }

  if (
    isMeetupError ||
    isReviewsError ||
    isPostReviewError ||
    isDeleteMeetupError
  ) {
    return <div>Todo 에러컴포넌트적용</div>
  }

  return (
    <>
      {isDeleteOpen && (
        <Modal onClick={() => setIsDeleteOpen(false)}>
          <ConfirmModal
            title="일정을 삭제하시겠습니까?"
            buttonText="일정 삭제"
            onClickDelete={onClickDeleteMeetup}
            onClickCloseModal={() => setIsDeleteOpen(false)}
          />
        </Modal>
      )}
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
          <MeetupMembers clubId={parsedClubId} meetupId={parsedMeetupId} />
          <MeetupAlbum
            clubId={parsedClubId}
            meetupId={parsedMeetupId}
            join={meetup?.join}
          />
          <MeetupReviewList
            clubId={parsedClubId}
            meetupId={parsedMeetupId}
            reviewInfo={reviews}
          />
        </div>
        {meetup?.join && (
          <TextSendBar
            placeholder="후기를 입력해주세요"
            content={content}
            setContent={setContent}
            onClick={onClickPostReview}
          />
        )}
        <div className={styles.btn}>
          {userInfo?.memberId === meetup.meetupHostId ? (
            <Button
              text="삭제"
              color="red"
              size="xs"
              onClick={() => setIsDeleteOpen(true)}
            />
          ) : null}
        </div>
      </div>
    </>
  )
}

export default MeetupDetailPage
