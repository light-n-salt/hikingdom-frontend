import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'

function ClubChatPage() {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`page p-sm ${theme} mobile`}>
      <PageHeader title="모임이름" url="/club/main" />
    </div>
  )
}

export default ClubChatPage
