import React from 'react'
import styles from './MeetupReviewItem.module.scss'
import { useParams } from 'react-router-dom'

import Image from 'components/common/Image'
import IconButton from 'components/common/IconButton'

import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { MeetupReview } from 'types/meetup.interface'
import { useRecoilValue } from 'recoil'
import { userInfoState } from 'recoil/atoms'

import { useMutation, useQueryClient } from '@tanstack/react-query'
import { deleteReview } from 'apis/services/meetup'
import toast from 'components/common/Toast'

type ReviewProps = {
  review: MeetupReview
}

function MeetupReviewItem({ review }: ReviewProps) {
  const userInfo = useRecoilValue(userInfoState)
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }
  const queryClient = useQueryClient()

  const onClickDelete = useMutation(
    () => deleteReview(parseInt(clubId), parseInt(meetupId), review.reviewId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['reviews'])
        toast.addMessage('success', '후기가 삭제되었습니다.')
      },
    }
  )

  return (
    <div className={styles.review}>
      <Image size="sm" imgUrl={review.profileUrl} isSquare={true} />

      <div className={styles.content}>
        <div className={styles.line}>
          <span className={styles.nickname}>{review.nickname}</span>

          <div className={styles.btns}>
            <div className={styles.siren}>
              <HiLightBulb /> 신고하기
            </div>
            {userInfo.memberId === review.memberId ? (
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
