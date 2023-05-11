import React, { useContext, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import useUserQuery from 'hooks/useUserQuery'
import ClubHeader from 'components/club/ClubHeader'

function ClubPage() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)

  const { data: userInfo } = useUserQuery()
  const clubId = useParams<string>().clubId

  const isJoin = userInfo?.clubId === Number(clubId)

  useEffect(() => {
    if (userInfo && !isJoin) {
      navigate(`/club/${clubId}/detail`)
    }
  }, [])

  return userInfo && isJoin ? (
    <div className={`page ${theme}`}>
      <ClubHeader />
      <Outlet />
    </div>
  ) : null
}

export default ClubPage
