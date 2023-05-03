import React, { useContext } from 'react'
import FindPwForm from 'components/auth/FindPwForm'
import Logo from 'components/common/Logo'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './FindPwPage.module.scss'

function FindPwdPage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page-gradation p-lg ${theme} ${styles.container}`}>
      <Logo />
      <FindPwForm />
    </div>
  )
}

export default FindPwdPage
