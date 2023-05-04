import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SearchBar.module.scss'
import { BsSearch } from 'react-icons/bs'

type SearchBarProps = {
  value: string
  placeholder: string
  setSelected?: (value: string) => void // selectbox 값 변경 함수
  onChangeText: (event: React.ChangeEvent<HTMLInputElement>) => void // 검색값
}

function SearchBar({
  value,
  placeholder,
  setSelected,
  onChangeText,
}: SearchBarProps) {
  const { theme } = useContext(ThemeContext)

  return (
    <div className={`content ${theme} ${styles.searchbar}`}>
      {setSelected ? <>toggle</> : <BsSearch className={`${styles.icon}`} />}
      <label htmlFor="input"> I </label>
      <input
        id="input"
        value={value}
        onChange={onChangeText}
        placeholder={placeholder}
      />
    </div>
  )
}

export default SearchBar
