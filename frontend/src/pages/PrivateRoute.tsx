import React from 'react'
import styles from './PrivateRoute.module.scss'
import { Navigate, Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'
import NavigationBar from 'components/common/NavigationBar'
import useUserQuery from 'hooks/useUserQuery'
import Loading from 'components/common/Loading'
import { useRecoilValue } from 'recoil'
import { refreshTokenState } from 'recoil/atoms'
import { useUserInfoQuery } from 'apis/services/users'
import toast from 'components/common/Toast'
import ErrorMessage from 'components/common/ErrorMessage'

/*
사용자 로그인 여부에 따라 로그인페이지로 라우팅하는 컴포넌트 
*/

export default function PrivateRoute() {
  const { data: userInfo, isLoading, isError } = useUserInfoQuery() // userInfo를 해당 컴포넌트에서 불러온다.

  // 로그인 여부 체크
  const refreshToken = useRecoilValue(refreshTokenState)
  if (!refreshToken) {
    return <Navigate to="/login" />
  }

  const isMobile = useIsMobile() // 모바일 여부 판단 커스텀 훅

  if (isLoading) {
    return <Loading />
  }

  if (isError) {
    return <ErrorMessage message="사용자 정보를 불러오지 못했습니다" />
  }

  return (
    <>
      {isMobile ? (
        <Outlet />
      ) : (
        <div className={styles.container}>
          <Outlet />
          <NavigationBar userInfo={userInfo} />
        </div>
      )}
    </>
  )
}
