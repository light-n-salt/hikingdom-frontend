import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'

function MtDetailPage() {
    const { theme } = useContext(ThemeContext)

    return (
        <div className={`page p-sm ${theme} mobile `}>
            <PageHeader title="" url="/mountain/search" color="light" />
        </div>
    )
}

export default MtDetailPage
