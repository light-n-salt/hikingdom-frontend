import React from 'react'
import styles from './IconButton.module.scss'

type IconButtonProps = {
  imgSrc?: string
  icon?: React.ReactNode
  color?: 'dark' | 'light' | 'primary' | 'gray'
  size?: 'sm' | 'md' | 'lg'
  onClick: () => void
}

function IconButton({
  imgSrc,
  icon,
  color = 'dark',
  size = 'sm',
  onClick,
}: IconButtonProps) {
  return (
    <div
      className={`${styles.btn} ${styles[size]} ${styles[color]}`}
      onClick={onClick}
    >
      {imgSrc && <img src={imgSrc} className={styles.img} />}
      {icon && icon}
    </div>
  )
}

export default IconButton
