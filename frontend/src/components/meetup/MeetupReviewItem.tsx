import React from 'react'
import styles from './MeetupReviewItem.module.scss'

import Image from 'components/common/Image'
import IconButton from 'components/common/IconButton'

import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { MeetupReview } from 'types/meetup.interface'

type ReviewProps = {
  review: MeetupReview
}
function MeetupReviewItem({ review }: ReviewProps) {
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
            <IconButton
              icon={<HiTrash />}
              size="sm"
              color="gray"
              onClick={() => console.log('hi')}
            />
          </div>
        </div>

        <div>{review.content}</div>
      </div>
    </div>
  )
}

export default MeetupReviewItem
