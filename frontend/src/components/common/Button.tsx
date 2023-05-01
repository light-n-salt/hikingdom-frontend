import React from 'react'
import styles from './Button.module.scss'

type ButtonProps = {
    text: string
    size?: 'sm' | 'md' | 'lg'
    color: 'primary' | 'secondary' | 'white' | 'red'
    onClick: () => void
}

export default function Button({
    text,
    size = 'lg',
    color,
    onClick,
}: ButtonProps) {
    return (
        <button
            className={`${styles[size]} ${styles[color]}`}
            onClick={onClick}
        >
            {text}
        </button>
    )
}
