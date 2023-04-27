import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './IconText.module.scss'

type IconTextProps = {
    imgSrc: string
    text: string
    bold?: boolean // font-weight : bold 설정
    size?: 'sm' | 'md' | 'lg'
    iconPosition?: 'right' // 아이콘 위치
}

function IconText({
    imgSrc,
    text,
    bold,
    size = 'sm',
    iconPosition,
}: IconTextProps) {
    const { theme } = useContext(ThemeContext)

    const textStyle = bold && styles.bold
    const flexStyle = iconPosition && styles[iconPosition]

    return (
        <div
            className={`${styles[theme]} ${styles[size]} ${textStyle} ${flexStyle}`}
        >
            <img src={imgSrc} />
            {text}
        </div>
    )
}

export default IconText
