import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './Main.module.scss'

export default function Main() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`box-${theme} ${styles[theme]} ${styles.flex}`}>
            <div>안녕하세요</div>
            <button onClick={toggleTheme}>다크모드</button>
        </div>
    )
}
