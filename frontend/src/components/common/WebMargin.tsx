import React from 'react'
import styles from './WebMargin.module.scss'
import { Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'
import ClubMountain from 'components/club/ClubMountain'
import { assetInfo } from 'recoil/assetInfo'

// 프로젝트의 동작환경을 판단하여
// PC 환경일 경우, width: 400px
// 모바일 환경일 경우, width:100%
function WebMargin() {
  const isMobile = useIsMobile() // 모바일 여부 판단 커스텀 훅

  return (
    <>
      {isMobile ? (
        <>
          <Outlet />
          <div id="modal-root" />
        </>
      ) : (
        <div className={styles.background}>
          <div className={styles.mountain}>
            <ClubMountain zoom={4} assetInfo={assetInfo} />
          </div>
          <div className={styles.mobile}>
            <Outlet />
            <div id="modal-root" />
          </div>
        </div>
      )}
    </>
  )
}

export default WebMargin
