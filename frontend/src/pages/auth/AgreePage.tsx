import AgreeForm from 'components/auth/AgreeForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './AgreePage.module.scss'

function AgreePage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page ${theme} p-sm ${styles.container}`}>
      <AgreeForm />
    </div>
  )
}

export default AgreePage
