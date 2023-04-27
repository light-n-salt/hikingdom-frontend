import React from 'react'
import styles from './IconButton.module.scss'

type IconButtonProps = {
    icon: string
    size?: 'sm' | 'md' | 'lg'
    onClick: () => void
}

function IconButton({ icon, size = 'sm', onClick }: IconButtonProps) {
    return (
        <img
            src={icon}
            className={`${styles.iconButton} ${styles[size]}`}
            onClick={onClick}
        />
    )
}

export default IconButton
