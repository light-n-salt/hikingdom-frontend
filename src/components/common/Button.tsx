import React from 'react'
import styles from './Button.module.scss'

type ButtonProps = {
    text: string
    size?: 'sm' | 'md' | 'lg'
    color: 'primary' | 'secondary' | 'white' | 'red' | 'gray'
    onClick?: () => void
    disabled?: boolean
}

export default function Button({
    text,
    size = 'lg',
    color,
    onClick = () => {},
    disabled = false,
}: ButtonProps) {
    return (
        <button
            className={`${styles[size]} ${styles[color]}`}
            onClick={onClick}
            onTouchEnd={onClick}
            disabled={disabled}
        >
            {text}
        </button>
    )
}
