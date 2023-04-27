import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
// import styles from './Main.module.scss'

export default function Main() {
    const [isModal, setIsModal] = useState(false)
    const { theme, toggleTheme } = useContext(ThemeContext)

    return <div className={`page ${theme} mobile`}></div>
}
