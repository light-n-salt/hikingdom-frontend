import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SearchClubPage.module.scss'

import SearchBar from 'components/common/SearchBar'
import RankList from 'components/common/RankList'

import { ClubInfo } from 'types/club.interface'

function SearchClubPage() {
  const { theme } = useContext(ThemeContext)

  const [value, setValue] = useState('')

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  return (
    <div className={`page ${theme} p-md ${styles.search}`}>
      <SearchBar value={value} placeholder="" onChange={onChange} />
      <RankList clubInfoArray={clubInfoArray} size="lg" />
    </div>
  )
}

export default SearchClubPage

const clubInfoArray: ClubInfo[] = [
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 15,
  },
  {
    clubId: 3,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 2,
  },
  {
    clubId: 13,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 15,
  },
  {
    clubId: 3,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 2,
  },
  {
    clubId: 13,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 34,
  },
]
