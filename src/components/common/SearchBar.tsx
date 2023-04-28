import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SearchBar.module.scss'
import { BsSearch } from 'react-icons/bs'

type SearchBarProps = {
    value: string
    placeholder: string
    setSeleted?: (value: string) => void // selectbox 값 변경 함수
    onChangeText: (value: string) => void // 검색값
}

function SearchBar({
    value,
    placeholder,
    setSeleted,
    onChangeText,
}: SearchBarProps) {
    const { theme } = useContext(ThemeContext)

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onChangeText(event.target.value)
    }

    return (
        <div className={`${styles[theme]} ${styles.searchbar}`}>
            {setSeleted ? (
                <>toggle</>
            ) : (
                <BsSearch className={`${styles.icon}`} />
            )}
            <label htmlFor="input"> I </label>
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
