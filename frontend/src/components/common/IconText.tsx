import React from 'react'
import styles from './IconText.module.scss'

type IconTextProps = {
    imgSrc: string
    text: string
    size?: 'sm' | 'md' | 'lg'
    isBold?: boolean // font-weight : bold 설정
    isRight?: boolean // 아이콘 위치
}

function IconText({
    imgSrc,
    text,
    size = 'sm',
    isBold = false,
    isRight = false,
}: IconTextProps) {
    const textStyle = isBold ? styles.bold : ''
    const flexStyle = isRight ? styles['right'] : ''

    return (
        <div
            className={`${styles['icon-text']} ${styles[size]} ${textStyle} ${flexStyle}`}
        >
            <img src={imgSrc} />
            {text}
        </div>
    )
}

export default IconText
