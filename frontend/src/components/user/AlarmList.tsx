import React from 'react'
import AlarmItem from 'components/user/AlarmItem'
import styles from './AlarmList.module.scss'
import { UserAlarm } from 'types/user.interface'

function AlarmList({ alarmList }: { alarmList: UserAlarm[] }) {
  return (
    <div className={`${styles['alarm-list']}`}>
      {alarmList.map((alarm) => (
        <AlarmItem key={alarm.notificationId} alarm={alarm} />
      ))}
    </div>
  )
}

export default AlarmList
