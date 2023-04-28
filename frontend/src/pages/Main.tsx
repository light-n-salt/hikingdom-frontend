import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import MtDetail from 'components/main/MtDetail'
// import styles from './Main.module.scss'

export default function Main() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`page ${theme} mobile`}>
            <MtDetail />
        </div>
    )
}
