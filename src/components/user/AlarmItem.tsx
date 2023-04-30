import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './AlarmItem.module.scss'

import { UserAlarm } from 'types/user.interface'

import { GoPrimitiveDot } from 'react-icons/go'

function AlarmItem({ alarm }: { alarm: UserAlarm }) {
    const { theme } = useContext(ThemeContext)

    const alarmStyle = alarm.isRead ? styles.read : styles.unread

    return (
        <div className={`box ${theme} ${styles['alarm-box']} ${alarmStyle}`}>
            <div className={`${styles['alarm-title-box']}`}>
                <div className={`${styles['alarm-title']} ${alarmStyle}`}>
                    <GoPrimitiveDot className={styles.dot} />
                    <span>{alarm.title}</span>
                </div>
                <span>{alarm.createdAt}</span>
            </div>
            <div className={`${styles['alarm-content']}`}>{alarm.content}</div>
        </div>
    )
}

export default AlarmItem
