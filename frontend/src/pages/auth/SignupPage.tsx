import SignupForm from 'components/auth/SignupForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SignupPage.module.scss'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import TextButton from 'components/common/TextButton'
import PageHeader from 'components/common/PageHeader'

function SignupPage() {
  const { theme, toggleTheme } = useContext(ThemeContext)

  return (
    <div className={`page-gradation p-lg ${theme} ${styles['singup-page']}`}>
      <PageHeader title="회원가입" url="/login" />
      <SignupForm />
    </div>
  )
}

export default SignupPage
