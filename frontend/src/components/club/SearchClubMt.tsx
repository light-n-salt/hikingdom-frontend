import React, { useState, useEffect } from 'react'
import styles from './SearchClubMt.module.scss'
import { AssetInfo } from 'types/club.interface'
// import SearchBar from 'components/common/SearchBar'
import Dropdown from 'components/common/Dropdown'
import ClubMountain from 'components/club/ClubMountain'
import SelectBox from 'components/common/SelectBox'
import { getPosition } from 'utils/getPosition'

type SearchClubMtProps = {
  assetInfo: AssetInfo[]
}

function SearchClubMt({ assetInfo }: SearchClubMtProps) {
  const [filterOptions, setFilterOptions] = useState<any[]>([])
  const [assetArray, setAssetArray] = useState<any[]>([])
  // const [value, setValue] = useState('')

  const [filter, setFilter] = useState('') // 선택된 필터 옵션 value

  useEffect(() => {
    const data = getPosition(assetInfo, filter)
    setAssetArray(data.arr)

    const options = data.uniqueNameList.map((name) => ({
      label: name,
      value: name,
    }))
    setFilterOptions(options)
    console.log('check')
  }, [filter])

  // function onChangeSetValue(event: React.ChangeEvent<HTMLInputElement>) {
  //   setValue(event.target.value)
  // }

  return (
    <>
      <ClubMountain zoom={3.5} assetInfo={assetArray} />
      <div className={styles.select}>
        <Dropdown
          options={filterOptions}
          setValue={setFilter}
          defaultLabel="전체"
        />
      </div>
      {/* <SearchBar
        value={value}
        placeholder="등산했던 산을 검색해보세요"
        onChange={onChangeSetValue}
      /> */}
    </>
  )
}

export default SearchClubMt
