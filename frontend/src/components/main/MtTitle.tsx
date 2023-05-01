import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtTitle.module.scss'

type MtTitleProps = {
    name: string
    maxAlt: number
    timeDuration: number
    hikingNumber: number
}

function MtTitle({ name, maxAlt, timeDuration, hikingNumber }: MtTitleProps) {
    const { theme, toggleTheme } = useContext(ThemeContext)
    return (
        <div className={`box ${theme} ${styles['mt-title']}`}>
            <span>{name}</span>
        </div>
    )
}

export default MtTitle
