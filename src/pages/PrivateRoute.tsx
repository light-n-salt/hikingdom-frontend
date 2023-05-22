import React from 'react'
import styles from './PrivateRoute.module.scss'
import { Navigate, Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'
import NavigationBar from 'components/common/NavigationBar'
import useUserQuery from 'hooks/useUserQuery'
import Loading from 'components/common/Loading'

/*
사용자 로그인 여부에 따라 로그인페이지로 라우팅하는 컴포넌트 
*/

export default function PrivateRoute() {
  const { data: userInfo, isLoading, isError } = useUserQuery() // userInfo를 해당 컴포넌트에서 불러온다.

  // 로그인 여부 체크
  const refreshToken = localStorage.getItem('refreshToken')
  if (!refreshToken) {
    return <Navigate to="/login" />
  }

  const isMobile = useIsMobile() // 모바일 여부 판단 커스텀 훅

  return (
    <>
      {userInfo ? (
        isMobile ? (
          <Outlet />
        ) : (
          <div className={styles.container}>
            <Outlet />
            <NavigationBar userInfo={userInfo} />
          </div>
        )
      ) : (
        <Loading />
      )}
    </>
  )
}
