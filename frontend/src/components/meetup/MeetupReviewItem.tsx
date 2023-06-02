import React, { useContext, useState } from 'react'
import styles from './MeetupReviewItem.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import Image from 'components/common/Image'

import { HiTrash, HiLightBulb } from 'react-icons/hi'
import { MeetupReview } from 'types/meetup.interface'

import useUserQuery from 'hooks/useUserQuery'
import { useMutation } from '@tanstack/react-query'
import { useDeleteReview } from 'apis/services/meetup'
import { report } from 'apis/services/users'
import toast from 'components/common/Toast'
import Modal from 'components/common/Modal'
import ConfirmModal from 'components/club/ConfirmModal'
import Loading from 'components/common/Loading'

type ReviewProps = {
  review: MeetupReview
  clubId: number
  meetupId: number
}

function MeetupReviewItem({ review, clubId, meetupId }: ReviewProps) {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()
  const [isDeleteOpen, setIsDeleteOpen] = useState(false)
  const [isSirenOpen, setIsSirenOpen] = useState(false)

  // 후기 삭제
  const {
    isLoading: isDeleteReviewLoading,
    isError: isDeleteReviewError,
    mutateAsync: deleteReview,
  } = useDeleteReview(clubId, meetupId, review.reviewId)
  const onClickDeleteReview = () => {
    deleteReview().then(() => setIsDeleteOpen(false))
  }

  const onclickReport = useMutation(() => report('REVIEW', review.reviewId), {
    onSuccess: () => {
      toast.addMessage('success', '신고가 완료되었습니다')
      setIsSirenOpen(false)
    },
    onError: () => {
      toast.addMessage('error', '이미 신고되었습니다')
      setIsSirenOpen(false)
    },
  })

  if (isDeleteReviewLoading) {
    return <Loading />
  }

  if (isDeleteReviewError) {
    return <div>Todo: 에러컴포넌트적용</div>
  }

  return (
    <>
      {isDeleteOpen && (
        <Modal onClick={() => setIsDeleteOpen(false)}>
          <ConfirmModal
            title="후기를 삭제하시겠습니까?"
            buttonText="후기 삭제"
            onClickDelete={onClickDeleteReview}
            onClickCloseModal={() => setIsDeleteOpen(false)}
          />
        </Modal>
      )}
      {isSirenOpen && (
        <Modal onClick={() => setIsSirenOpen(false)}>
          <ConfirmModal
            title="후기를 신고하시겠습니까?"
            buttonText="후기 신고"
            onClickDelete={() => onclickReport.mutate()}
            onClickCloseModal={() => setIsSirenOpen(false)}
          />
        </Modal>
      )}
      <div className={`${styles[theme]} ${styles.review}`}>
        <Image size="sm" imgUrl={review.profileUrl} isSquare={true} />

        <div className={styles.content}>
          <div className={styles.line}>
            <span className={styles.nickname}>{review.nickname}</span>

            <div className={styles.btns}>
              {userInfo && userInfo.memberId === review.memberId ? (
                <div
                  className={`${styles.siren} ${styles.gray}`}
                  onClick={() => setIsDeleteOpen(true)}
                >
                  <HiTrash />
                </div>
              ) : (
                <div
                  className={`${styles.siren} ${styles.red}`}
                  onClick={() => setIsSirenOpen(true)}
                >
                  <HiLightBulb /> 신고하기
                </div>
              )}
            </div>
          </div>
          <div>{review.content}</div>
        </div>
      </div>
    </>
  )
}

export default MeetupReviewItem
