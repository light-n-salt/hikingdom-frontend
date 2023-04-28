import SignupForm from 'components/auth/SignupForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SignupPage.module.scss'

function SignupPage() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`page ${theme} ${styles['singup-page']}`}>
            <div className={`login-background`} />
            <button onClick={toggleTheme}>다크모드</button>
            <SignupForm />
        </div>
    )
}

export default SignupPage
