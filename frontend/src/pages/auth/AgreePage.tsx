import AgreeForm from 'components/auth/AgreeForm'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

function AgreePage() {
  const { theme, toggleTheme } = useContext(ThemeContext)
  return (
    <div className={`page ${theme} p-sm`}>
      <AgreeForm />
    </div>
  )
}

export default AgreePage
