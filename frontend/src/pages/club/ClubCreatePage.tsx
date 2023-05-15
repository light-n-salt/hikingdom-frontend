import CreateClubForm from 'components/club/CreateClubForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

function ClubCreatePage() {
  const { theme } = useContext(ThemeContext)

  return (
    <div className={`page p-lg ${theme}`}>
      <CreateClubForm />
    </div>
  )
}

export default ClubCreatePage
