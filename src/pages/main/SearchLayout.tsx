import React, { useContext, useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import SearchBar from 'components/common/SearchBar'

import SearchMtPage from './SearchMtPage'
import MtList from 'components/common/MtList'
import useDebounce from 'hooks/useDebounce'
import { getMountains } from 'apis/services/mountains'
import MainContent from 'components/main/MainContent'

import styles from './SearchLayout.module.scss'

function SearchLayout() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const [value, setValue] = useState('')

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
    if (event.target.value) {
      navigate(`/main/search`, {
        state: { query: event.target.value },
      })
    } else {
      navigate(`/main`)
    }
  }

  return (
    <div className={`page ${theme} p-md ${styles.container}`}>
      <div className={styles.searchbar}>
        <SearchBar
          value={value}
          placeholder="산을 검색해보세요"
          onChange={onChange}
        />
      </div>
      <Outlet context={{ value }} />
    </div>
  )
}

export default SearchLayout
