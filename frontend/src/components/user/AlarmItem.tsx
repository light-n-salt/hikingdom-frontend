import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './AlarmItem.module.scss'

import { UserAlarm } from 'types/user.interface'

import { GoPrimitiveDot } from 'react-icons/go'

function AlarmItem({ alarm }: { alarm: UserAlarm }) {
  const { theme } = useContext(ThemeContext)

  // read, unread 구분
  const alarmStyle = alarm.isRead ? styles.read : styles.unread

  // 시간
  const date = alarm.sendAt.split(' ')[0].replaceAll('-', '.')
  const time = alarm.sendAt.split(' ')[1]
  const notiTime = `${date.slice(2, date.length)} ${time.slice(
    -5,
    time.length
  )}`

  return (
    <div className={`content ${theme} ${styles.alarm} ${alarmStyle}`}>
      <div className={`${styles['alarm-title-box']}`}>
        <div className={`${styles.title} ${alarmStyle}`}>
          <GoPrimitiveDot className={styles.dot} />
          <span>{alarm.body}</span>
        </div>
        <span>{notiTime}</span>
      </div>
      <div className={`${styles.content}`}>{alarm.title}</div>
    </div>
  )
}

export default AlarmItem
