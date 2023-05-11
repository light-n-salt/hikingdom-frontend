import CreateMeetupForm from 'components/club/CreateMeetupForm'
import { ThemeContext } from 'styles/ThemeProvider'
import React, { useContext } from 'react'
import PageHeader from 'components/common/PageHeader'
import { useParams } from 'react-router'

function CreateMeetupPage() {
  const { clubId } = useParams()
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page p-lg ${theme}`}>
      <PageHeader
        title="일정 만들기"
        url={`/club/${clubId}/meetup`}
        color="primary"
      />
      <CreateMeetupForm />
    </div>
  )
}

export default CreateMeetupPage
