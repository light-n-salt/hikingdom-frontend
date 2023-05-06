import React, { useContext } from 'react'
import styles from './LoginPage.module.scss'
import Logo from 'components/common/Logo'
import LoginForm from 'components/auth/LoginForm'
import { ThemeContext } from 'styles/ThemeProvider'

function LoginPage() {
  const { theme } = useContext(ThemeContext)

  return (
    <div className={`page-gradation p-lg ${theme} ${styles.container}`}>
      <Logo />
      <LoginForm />
    </div>
  )
}

export default LoginPage
