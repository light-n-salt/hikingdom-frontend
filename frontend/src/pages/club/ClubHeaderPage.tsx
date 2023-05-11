import React, { useContext, useEffect } from 'react'
import { Outlet } from 'react-router'
import { useNavigate } from 'react-router-dom'
import Loading from 'components/common/Loading'
import ClubHeader from 'components/club/ClubHeader'
import useUserQuery from 'hooks/useUserQuery'
import { ThemeContext } from 'styles/ThemeProvider'

function ClubHeaderPage() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery() // user정보 불러오기

  useEffect(() => {
    if (!userInfo) return // userInfo 자체가 없을 경우
    if (!userInfo.clubId) {
      navigate(`/club/none`) // userInfo 의 clubId가 없을 경우, none 페이지로 이동
    }
  }, [userInfo])

  return userInfo ? (
    <div className={`page ${theme}`}>
      <ClubHeader />
      <Outlet />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubHeaderPage
