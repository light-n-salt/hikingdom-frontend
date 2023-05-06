import React from 'react'
import styles from './RankList.module.scss'
import RankItem from 'components/common/RankItem'
import { ClubInfo } from 'types/club.interface'

type RankListProps = {
  clubInfoArray: ClubInfo[] // 소모임 정보 배열
  size?: 'sm' | 'lg' // 사이즈
  isButton?: boolean // 소모임 삭제버튼 여부
}

function RankList({
  clubInfoArray,
  size = 'lg',
  isButton = false,
}: RankListProps) {
  return (
    <div className={`${styles.container} ${styles[size]}`}>
      {clubInfoArray.map((clubInfo) => (
        <RankItem
          key={clubInfo.clubId}
          clubInfo={clubInfo}
          size={size}
          isButton={isButton}
        />
      ))}
    </div>
  )
}

export default RankList
