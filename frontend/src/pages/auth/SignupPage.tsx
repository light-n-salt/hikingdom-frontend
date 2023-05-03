import SignupForm from 'components/auth/SignupForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SignupPage.module.scss'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import TextButton from 'components/common/TextButton'

function SignupPage() {
  const { theme, toggleTheme } = useContext(ThemeContext)

  return (
    <div className={`page-gradation ${theme} ${styles['singup-page']}`}>
      <TextButton
        color="primary"
        text="button"
        onClick={() => toast.addMessage('success', '성공시 문구')}
      />
      <h3>회원가입</h3>
      <SignupForm />
    </div>
  )
}

export default SignupPage
