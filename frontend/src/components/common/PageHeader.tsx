import React from 'react'
import styles from './PageHeader.module.scss'
import { useNavigate } from 'react-router-dom'

import { FiChevronLeft } from 'react-icons/fi'

type PageHeaderProps = {
  title: string
  url: string // 이동할 URL 주소
  color?: 'dark' | 'light' | 'primary'
  size?: 'sm' | 'md' | 'lg'
}

function PageHeader({
  title,
  url,
  color = 'dark',
  size = 'md',
}: PageHeaderProps) {
  const navigate = useNavigate()

  return (
    <div className={`${styles.container} ${styles[color]}`}>
      <div className={styles.icon}>
        <FiChevronLeft onClick={() => navigate(url)} />
      </div>
      <div className={`${styles.title} ${styles[size]}`}>{title}</div>
    </div>
  )
}

export default PageHeader
