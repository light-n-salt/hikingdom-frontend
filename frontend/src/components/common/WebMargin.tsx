import React from 'react'
import styles from './WebMargin.module.scss'
import { Outlet } from 'react-router-dom'
import useIsMobile from 'hooks/useIsMobile'

function WebMargin() {
  const isMobile = useIsMobile()

  return (
    <div className={styles.container}>
      <div className={isMobile ? styles.mobile : styles.laptop}>
        <Outlet />
      </div>
    </div>
  )
}

export default WebMargin
