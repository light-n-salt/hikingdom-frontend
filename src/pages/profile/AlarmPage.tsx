import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import { UserAlarm } from 'types/user.interface'

import PageHeader from 'components/common/PageHeader'
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
        <div className={`page p-sm ${theme} mobile `}>
            <PageHeader title="알림 내역" url="/profile" />
            <AlarmList alarmList={alarmList} />
        </div>
    )
}

export default AlarmPage
