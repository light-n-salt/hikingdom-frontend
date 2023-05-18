import React, { useContext } from 'react'
import styles from './MeetupReviewItem.module.scss'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import Image from 'components/common/Image'
import IconButton from 'components/common/IconButton'

import { HiTrash, HiLightBulb } from 'react-icons/hi'
import { MeetupReview } from 'types/meetup.interface'

import useUserQuery from 'hooks/useUserQuery'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { deleteReview } from 'apis/services/meetup'
import { report } from 'apis/services/users'
import toast from 'components/common/Toast'

type ReviewProps = {
  review: MeetupReview
}

function MeetupReviewItem({ review }: ReviewProps) {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()
  const { meetupId } = useParams() as { meetupId: string }
  const queryClient = useQueryClient()

  const onClickDelete = useMutation(
    () =>
      deleteReview(Number(userInfo?.clubId), Number(meetupId), review.reviewId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['reviews'])
        toast.addMessage('success', '후기가 삭제되었습니다')
      },
    }
  )

  const onclickReport = useMutation(() => report('REVIEW', review.reviewId), {
    onSuccess: () => {
      toast.addMessage('success', '신고가 완료되었습니다')
    },
  })

  return (
    <div className={`${styles[theme]} ${styles.review}`}>
      <Image size="sm" imgUrl={review.profileUrl} isSquare={true} />

      <div className={styles.content}>
        <div className={styles.line}>
          <span className={styles.nickname}>{review.nickname}</span>

          <div className={styles.btns}>
            <div
              className={styles.siren}
              onClick={() => onclickReport.mutate()}
            >
              <HiLightBulb /> 신고하기
            </div>
            {userInfo && userInfo.memberId === review.memberId ? (
              <IconButton
                icon={<HiTrash />}
                size="sm"
                color="gray"
                onClick={() => onClickDelete.mutate()}
              />
            ) : null}
          </div>
        </div>
        <div>{review.content}</div>
      </div>
    </div>
  )
}

export default MeetupReviewItem
