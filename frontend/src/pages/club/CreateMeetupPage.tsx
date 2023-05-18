import CreateMeetupForm from 'components/club/CreateMeetupForm'
import { ThemeContext } from 'styles/ThemeProvider'
import React, { useContext } from 'react'
import styles from './CreateMeetupPage.module.scss'
import PageHeader from 'components/common/PageHeader'

function CreateMeetupPage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page p-lg ${theme} ${styles.container}`}>
      <PageHeader title="일정 만들기" url={`/club/meetup`} color="primary" />
      <CreateMeetupForm />
    </div>
  )
}

export default CreateMeetupPage
