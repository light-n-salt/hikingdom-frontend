import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import SearchBar from 'components/common/SearchBar'

import styles from './MountainPage.module.scss'
import SearchMtPage from './SearchMtPage'
import MainPage from './MainPage'

function MountainPage() {
    const navigate = useNavigate()

    const { theme } = useContext(ThemeContext)

    const [value, setValue] = useState('')

    return (
        <div className={`page ${theme} p-md ${styles.main}`}>
            <SearchBar
                value={value}
                placeholder="산을 검색해보세요"
                onChangeText={setValue}
            />
            {value ? <SearchMtPage /> : <MainPage />}
        </div>
    )
}

export default MountainPage
