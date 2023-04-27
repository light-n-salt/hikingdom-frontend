import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SearchBar.module.scss'

type SearchBarProps = {
    label: string
    value: string
    placeholder: string
    onChangeText: (value: string) => void
}

function SearchBar({ value, placeholder, onChangeText }: SearchBarProps) {
    const { theme } = useContext(ThemeContext)

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onChangeText(event.target.value)
    }

    return (
        <div className={`${styles[theme]}`}>
            <input
                id="input"
                value={value}
                onChange={handleChange}
                placeholder={placeholder}
            />
        </div>
    )
}

export default SearchBar
