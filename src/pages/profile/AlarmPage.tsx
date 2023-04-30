import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './AlarmPage.module.scss'
import { UserAlarm } from 'types/user.interface'

import AlarmList from 'components/user/AlarmList'

function AlarmPage() {
    const { theme } = useContext(ThemeContext)

    const alarmList: UserAlarm[] = [
        {
            alarmId: 0,
            title: '일정이 추가되었습니다.',
            content: '정예지렁이님이 8월 31일 새로운 일정을 추가하셨습니다.',
            createdAt: '2017.07.15 오후 12:34',
            isRead: false,
        },
        {
            alarmId: 1,
            title: '일정이 추가되었습니다.',
            content: '정예지렁이님이 8월 31일 새로운 일정을 추가하셨습니다.',
            createdAt: '2017.07.15 오후 12:34',
            isRead: true,
        },
    ]

    return (
        <div className={`page ${theme} mobile ${styles['alarm-page']}`}>
            <AlarmList alarmList={alarmList} />
        </div>
    )
}

export default AlarmPage
