import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'

function MeetupCreatePage() {
    const { theme } = useContext(ThemeContext)

    return (
        <div className={`page p-sm ${theme} mobile`}>
            <PageHeader
                title="일정 만들기"
                url="/club/meetup"
                color="primary"
            />
        </div>
    )
}

export default MeetupCreatePage
