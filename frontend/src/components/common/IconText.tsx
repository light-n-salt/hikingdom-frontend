import React from 'react'
import styles from './IconText.module.scss'

type IconTextProps = {
  imgSrc?: string
  icon?: React.ReactNode
  text: string
  size?: 'sm' | 'md' | 'lg'
  isBold?: boolean // font-weight : bold 설정
  isRight?: boolean // 아이콘 위치
}

function IconText({
  imgSrc,
  icon,
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
      {imgSrc && <img src={imgSrc} />}
      {icon && icon}
      {text}
    </div>
  )
}

export default IconText
