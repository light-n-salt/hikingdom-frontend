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
  const { clubId } = useParams()
  const navigate = useNavigate()

  const [date, time] = meetupInfo.startAt.split(' ')

  return (
    <div
      className={`content ${theme} ${styles.container}`}
      onClick={() =>
        navigate(`/club/${clubId}/meetup/${meetupInfo.meetupId}/detail`)
      }
    >
      <div className={styles.header}>
        <h3>{meetupInfo.meetupName}</h3>
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
