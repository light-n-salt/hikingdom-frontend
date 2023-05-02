import React from 'react'
import styles from './PageHeader.module.scss'
import { useNavigate } from 'react-router-dom'

import { FiChevronLeft } from 'react-icons/fi'

type PageHeaderProps = {
  title: string
  url: string // 이동할 URL 주소
  color?: 'dark' | 'light' | 'primary'
}

function PageHeader({ title, url, color = 'dark' }: PageHeaderProps) {
  const navigate = useNavigate()
  const textStyle = styles[color]

  return (
    <div className={`${styles.header} ${textStyle}`}>
      <FiChevronLeft onClick={() => navigate(url)} />
      <span>{title}</span>
    </div>
  )
}

export default PageHeader
