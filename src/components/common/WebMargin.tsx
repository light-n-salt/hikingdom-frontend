import React from 'react'
import styles from './WebMargin.module.scss'
import { Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'

function WebMargin() {
  const isMobile = useIsMobile()

  return (
    <>
      {isMobile ? (
        <Outlet />
      ) : (
        <div className={styles.container}>
          <div className={styles.mobile}>
            <Outlet />
          </div>
        </div>
      )}
    </>
  )
}

export default WebMargin
