import React from 'react'
import styles from './SearchBar.module.scss'
import { BiSearch } from 'react-icons/bi'

type SearchBarProps = {
  value: string
  placeholder: string
  // setSelected?: (value: string) => void // selectbox 값 변경 함수
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void // 검색값
}

function SearchBar({
  value,
  placeholder,
  // setSelected,
  onChange,
}: SearchBarProps) {
  return (
    <div className={`${styles.searchbar}`}>
      <BiSearch className={`${styles.icon}`} />
      <input
        id="input"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className={`${styles.input}`}
      />
    </div>
  )
}

export default SearchBar
