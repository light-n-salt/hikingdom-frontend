import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import SearchBar from 'components/common/SearchBar'
// import styles from './Main.module.scss'

export default function Main() {
    const [isModal, setIsModal] = useState(false)
    const { theme, toggleTheme } = useContext(ThemeContext)
    const [value, setValue] = useState('')

    return (
        <div className={`page ${theme} mobile`}>
            <SearchBar
                label="label"
                value={value}
                placeholder="입력하세요"
                onChangeText={setValue}
            />
            <>{value}</>
        </div>
    )
}
