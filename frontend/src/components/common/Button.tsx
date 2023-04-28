import React, { useContext } from 'react'
import styles from './Button.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

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
    const { theme } = useContext(ThemeContext)
    return (
        <button
            className={`${styles[theme]} ${styles[size]} ${styles[color]}`}
            onClick={onClick}
        >
            {text}
        </button>
    )
}
