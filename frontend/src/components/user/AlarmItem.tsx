import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './AlarmItem.module.scss'
import { useNavigate } from 'react-router'
import { UserAlarm } from 'types/user.interface'

import { GoPrimitiveDot } from 'react-icons/go'

function AlarmItem({ alarm }: { alarm: UserAlarm }) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  // read, unread 구분
  const alarmStyle = alarm.isRead ? styles.read : styles.unread

  // 시간
  const date = alarm.sendAt.split(' ')[0].replaceAll('-', '.')
  const time = alarm.sendAt.split(' ')[1]
  const notiTime = `${date.slice(2, date.length)} ${time.slice(
    -5,
    time.length
  )}`

  const onClick = () => {
    navigate(alarm.url)
  }

  return (
    <div
      className={`content ${theme} ${styles.alarm} ${alarmStyle}`}
      onClick={onClick}
    >
      <div className={`${styles['alarm-title-box']}`}>
        <div className={`${styles.title} ${alarmStyle}`}>
          <GoPrimitiveDot className={styles.dot} />
          <span>{alarm.title}</span>
        </div>
        <div className={styles.time}>{notiTime}</div>
      </div>
      <div className={`${styles.content}`}>{alarm.body}</div>
    </div>
  )
}

export default AlarmItem
