import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtContent.module.scss'

function MtContent() {
    const { theme, toggleTheme } = useContext(ThemeContext)
    return <div className={`box ${theme} ${styles['mt-title']}`}>MtContent</div>
}

export default MtContent
