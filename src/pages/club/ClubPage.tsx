import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import ClubHeader from 'components/club/ClubHeader'

function ClubPage() {
  const navigate = useNavigate()

  const { theme } = useContext(ThemeContext)

  return (
    <div className={`page ${theme} p-md`}>
      <ClubHeader />
    </div>
  )
}

export default ClubPage
