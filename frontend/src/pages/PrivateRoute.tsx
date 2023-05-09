import React from 'react'
import styles from './PrivateRotue.module.scss'
import { Navigate, Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'
import NavigationBar from 'components/common/NavigationBar'

/*
사용자 로그인 여부에 따라 로그인페이지로 라우팅하는 컴포넌트 
*/

export default function PrivateRoute() {
  // 로그인 여부 체크
  const isMobile = useIsMobile() // 모바일 여부 판단 커스텀 훅

  return (
    <>
      {isMobile ? (
        <Outlet />
      ) : (
        <div className={styles.container}>
          <Outlet />
          <NavigationBar />
        </div>
      )}
    </>
  )
}
