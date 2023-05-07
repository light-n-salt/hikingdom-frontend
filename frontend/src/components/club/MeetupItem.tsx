import React, { useContext } from 'react'
import styles from './MeetupItem.module.scss'
import clock from 'assets/images/clock.png'
import person from 'assets/images/person.png'
import mountain from 'assets/images/mountain.png'
import calendar from 'assets/images/calendar.png'
import IconText from 'components/common/IconText'
import { ThemeContext } from 'styles/ThemeProvider'
import { MeetupInfo } from 'types/meetup.interface'

type MeetupItemProps = {
  meetupInfo: MeetupInfo
}

function MeetupItem({ meetupInfo }: MeetupItemProps) {
  const { theme } = useContext(ThemeContext)
  const [date, time] = meetupInfo.startAt.split(' ')

  return (
    <div className={`content ${theme} ${styles.container}`}>
      <div className={styles.header}>
        <h3>{meetupInfo.meetupName}</h3>
        <IconText
          imgSrc={person}
          text={meetupInfo.totalMmember.toString()}
          size="sm"
          isBold={true}
        />
      </div>
      <div className={styles.flexbox}>
        <IconText
          imgSrc={mountain}
          text={meetupInfo.mountainName}
          size="sm"
          isBold={true}
        />
        <IconText imgSrc={calendar} text={date} size="sm" isBold={true} />
        <IconText imgSrc={clock} text={time} size="sm" isBold={true} />
      </div>
      {meetupInfo.description.length > 100
        ? meetupInfo.description.slice(100) + '...'
        : meetupInfo.description}
    </div>
  )
}

export default MeetupItem
