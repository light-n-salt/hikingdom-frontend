import React from 'react'
import styles from './PageHeader.module.scss'
import { useNavigate } from 'react-router-dom'

import { FiChevronLeft } from 'react-icons/fi'

type PageHeaderProps = {
    text: string
    url: string // 이동할 URL 주소
    color?: 'black' | 'primary'
}

function PageHeader({ text, url, color = 'black' }: PageHeaderProps) {
    const navigate = useNavigate()
    const textStyle = styles[color]

    return (
        <div className={`${styles.header} ${textStyle}`}>
            <FiChevronLeft onClick={() => navigate(url)} />
            <span>{text}</span>
        </div>
    )
}

export default PageHeader
