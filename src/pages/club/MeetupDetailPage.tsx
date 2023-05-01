import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'

function MeetupDetailPage() {
    const { theme } = useContext(ThemeContext)

    return (
        <div className={`page p-sm ${theme} mobile`}>
            <PageHeader title="일정 제목" url="/club/meetup" color="primary" />
        </div>
    )
}

export default MeetupDetailPage
