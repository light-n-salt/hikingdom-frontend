import React, { useContext } from 'react'
import styles from './AlarmPage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { UserAlarm } from 'types/user.interface'

import PageHeader from 'components/common/PageHeader'
import AlarmList from 'components/user/AlarmList'

import useUserQuery from 'hooks/useUserQuery'
import { useQuery } from '@tanstack/react-query'
import { getAlarms } from 'apis/services/users'

function AlarmPage() {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()

  const { data: alarmList } = useQuery<UserAlarm[]>(['alarms'], () =>
    getAlarms()
  )

  return (
    <div className={`page p-md ${theme}`}>
      <PageHeader
        color="primary"
        title="알림 내역"
        url={`/profile/${userInfo?.nickname}`}
      />
      {alarmList && <AlarmList alarmList={alarmList} />}
    </div>
  )
}

export default AlarmPage
