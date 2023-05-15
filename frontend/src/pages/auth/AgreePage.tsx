import React, { useContext } from 'react'
import styles from './AgreePage.module.scss'
import AgreeForm from 'components/auth/AgreeForm'
import { ThemeContext } from 'styles/ThemeProvider'

function AgreePage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page ${theme} p-lg ${styles.container}`}>
      <AgreeForm />
    </div>
  )
}

export default AgreePage
