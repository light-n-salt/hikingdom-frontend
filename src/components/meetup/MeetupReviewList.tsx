import React from 'react'
import styles from './MeetupReviewList.module.scss'

import MeetupReviewItem from './MeetupReviewItem'

import { MeetupReview } from 'types/meetup.interface'

type MeetupReviewsProps = {
  reviewInfo: MeetupReview[]
}

function MeetupReviewList({ reviewInfo }: MeetupReviewsProps) {
  return reviewInfo.length ? (
    <div className={styles.reviews}>
      {reviewInfo.map((review) => (
        <MeetupReviewItem key={review.reviewId} review={review} />
      ))}
    </div>
  ) : (
    <div>후기를 등록해주세요</div>
  )
}

export default MeetupReviewList
