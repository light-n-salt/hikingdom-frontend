import React from 'react'
import styles from './MeetupDetail.module.scss'

import IconText from 'components/common/IconText'

import mount from 'assets/images/mountain.png'
import calendar from 'assets/images/calendar.png'
import clock from 'assets/images/clock.png'

type MeetupDetailProps = {
  mountain: string
  date: string
  time: string
}

function MeetupDetail({ mountain, date, time }: MeetupDetailProps) {
  return (
    <div className={styles.detail}>
      <IconText imgSrc={mount} text={mountain} size="sm" />
      <IconText imgSrc={calendar} text={date} size="sm" />
      <IconText imgSrc={clock} text={time} size="sm" />
    </div>
  )
}

export default MeetupDetail
