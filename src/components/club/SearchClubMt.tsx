import React, { useState } from 'react'
import styles from './SearchClubMt.module.scss'
import { AssetInfo } from 'types/club.interface'
import SearchBar from 'components/common/SearchBar'
import ClubMoutain from 'components/club/ClubMoutain'

function SearchClubMt({ assetInfo }: AssetInfo) {
  const [value, setValue] = useState('')

  function onChangeSetValue(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  return (
    <>
      <SearchBar
        value={value}
        placeholder="등산했던 산을 검색해보세요"
        onChange={onChangeSetValue}
      />
      <ClubMoutain zoom={5} />
    </>
  )
}

export default SearchClubMt
