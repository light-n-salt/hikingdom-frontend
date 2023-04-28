import React from 'react'
import styles from './IconButton.module.scss'

type IconButtonProps = {
    imgSrc: string
    size?: 'sm' | 'md' | 'lg'
    onClick: () => void
}

function IconButton({ imgSrc, size = 'sm', onClick }: IconButtonProps) {
    return (
        <img
            src={imgSrc}
            className={`${styles['icon-btn']} ${styles[size]}`}
            onClick={onClick}
        />
    )
}

export default IconButton
