import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import ClubHeader from 'components/club/ClubHeader'

function ClubPage() {
  const { theme } = useContext(ThemeContext)

  // const navigate = useNavigate()

  return (
    <div className={`page ${theme} p-md`}>
      <ClubHeader />
      <Outlet></Outlet>
    </div>
  )
}

export default ClubPage
