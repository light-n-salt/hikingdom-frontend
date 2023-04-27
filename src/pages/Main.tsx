import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
// import styles from './Main.module.scss'

export default function Main() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`page ${theme} mobile`}>
            <button onClick={toggleTheme}>다크모드</button>
        </div>
    )
}
