import CreateMeetupForm from 'components/club/CreateMeetupForm'
import { ThemeContext } from 'styles/ThemeProvider'
import React, { useContext } from 'react'

function CreateMeetupPage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page p-lg ${theme}`}>
      <CreateMeetupForm />
    </div>
  )
}

export default CreateMeetupPage
