import React from 'react'
import { ClubInfo } from 'types/club.interface'
import RankItem from './RankItem'
import styles from './RankList.module.scss'

type RankListProps = {
  clubInfoArray: ClubInfo[]
  size?: 'sm' | 'lg'
  isButton?: boolean
}

function RankList({
  clubInfoArray,
  size = 'lg',
  isButton = false,
}: RankListProps) {
  return (
    <div className={`${styles['rank-list']} ${styles[size]}`}>
      {clubInfoArray.map((clubInfo, index) => (
        <RankItem
          key={`rank-item-${index}`}
          clubInfo={clubInfo}
          size={size}
          isButton={isButton}
        />
      ))}
    </div>
  )
}

export default RankList
