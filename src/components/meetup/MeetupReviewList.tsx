import React from 'react'
import styles from './MeetupReviewList.module.scss'

import MeetupReviewItem from './MeetupReviewItem'

import { MeetupReview } from 'types/meetup.interface'

type MeetupReviewsProps = {
  reviewInfo: MeetupReview[]
}

function MeetupReviewList({ reviewInfo }: MeetupReviewsProps) {
  return (
    <div className={styles.reviews}>
      {reviewInfo.map((review) => (
        <MeetupReviewItem key={review.reviewId} review={review} />
      ))}
    </div>
  )
}

export default MeetupReviewList
