import SignupForm from 'components/auth/SignupForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SignupPage.module.scss'

function SignupPage() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`page-gradation ${theme} ${styles['singup-page']}`}>
            <h3>회원가입</h3>
            <SignupForm />
        </div>
    )
}

export default SignupPage
