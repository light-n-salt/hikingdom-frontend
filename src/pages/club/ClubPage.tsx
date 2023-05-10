import React, { useContext, useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'
import { userInfoState } from 'recoil/atoms'
import { useRecoilValue } from 'recoil'
import ClubHeader from 'components/club/ClubHeader'

function ClubPage() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)

  const userInfo = useRecoilValue(userInfoState)
  const clubId = useParams<string>().clubId

  const isJoin = userInfo.clubId === Number(clubId)

  useEffect(() => {
    if (!isJoin) {
      navigate(`/club/${clubId}/detail`)
    }
  }, [])

  return isJoin ? (
    <div className={`page ${theme}`}>
      <ClubHeader />
      <Outlet />
    </div>
  ) : null
}

export default ClubPage
