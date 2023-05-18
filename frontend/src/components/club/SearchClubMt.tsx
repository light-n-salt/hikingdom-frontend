import React, { useState, useEffect } from 'react'
import styles from './SearchClubMt.module.scss'
import { AssetInfo } from 'types/club.interface'
import Dropdown from 'components/common/Dropdown'
import ClubMountain from 'components/club/ClubMountain'
import { getPosition } from 'utils/getPosition'

type SearchClubMtProps = {
  assetInfo: AssetInfo[]
}

function SearchClubMt({ assetInfo }: SearchClubMtProps) {
  const [filterOptions, setFilterOptions] = useState<any[]>([])
  const [assetArray, setAssetArray] = useState<any[]>([])

  const [filter, setFilter] = useState('') // 선택된 필터 옵션 value

  useEffect(() => {
    const data = getPosition(assetInfo, filter)
    setAssetArray(data.arr)

    const options = data.uniqueNameList.map((name) => ({
      label: name,
      value: name,
    }))
    setFilterOptions(options)
  }, [filter])

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
    </>
  )
}

export default SearchClubMt
