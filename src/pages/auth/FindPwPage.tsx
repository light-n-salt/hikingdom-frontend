import React, { useContext } from 'react'
import styles from './FindPwPage.module.scss'
import Logo from 'components/common/Logo'
import FindPwForm from 'components/auth/FindPwForm'
import { ThemeContext } from 'styles/ThemeProvider'

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
