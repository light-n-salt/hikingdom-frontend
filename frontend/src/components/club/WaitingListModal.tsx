import React, { useEffect } from 'react'
import styles from './WaitingListModal.module.scss'
import RankList from 'components/common/RankList'

import { ClubInfo } from 'types/club.interface'

function WaitingListModal() {
  return (
    <div className={styles.list}>
      <h2>가입 대기 중 모임</h2>
      <RankList clubInfoArray={clubInfoArray} size="lg" isButton={true} />
    </div>
  )
}

export default WaitingListModal

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
]
