import React, { useContext } from 'react'
import styles from './MeetupItem.module.scss'
import clock from 'assets/images/clock.png'
import person from 'assets/images/person.png'
import mountain from 'assets/images/mountain.png'
import calendar from 'assets/images/calendar.png'
import IconText from 'components/common/IconText'
import { ThemeContext } from 'styles/ThemeProvider'
import { MeetupInfo } from 'types/meetup.interface'
import { useNavigate, useParams } from 'react-router'

type MeetupItemProps = {
  meetupInfo: MeetupInfo
}

function MeetupItem({ meetupInfo }: MeetupItemProps) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const date = meetupInfo.startAt.split(' ')[0].replaceAll('-', '.').slice(-8)
  const time = meetupInfo.startAt.split(' ')[1].slice(0, 5)

  return (
    <div
      className={`content ${theme} ${styles.container}`}
      onClick={() => navigate(`/club/meetup/${meetupInfo.meetupId}/detail`)}
    >
      <div className={styles.header}>
        <div className={styles.title}>{meetupInfo.meetupName}</div>
        <IconText
          imgSrc={person}
          text={meetupInfo.totalMember.toString()}
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
        ? meetupInfo.description.slice(0, 100) + '...'
        : meetupInfo.description}
    </div>
  )
}

export default MeetupItem
