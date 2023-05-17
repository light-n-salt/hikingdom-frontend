import React from 'react'
import styles from './RankList.module.scss'
import RankItem from 'components/common/RankItem'
import { ClubInfo } from 'types/club.interface'

type RankListProps = {
  clubInfoArray: ClubInfo[] // 소모임 정보 배열
  size?: 'sm' | 'lg' // 사이즈
  onClickDeleteClub?: (clubId: number, clubName: string) => void // 소모임 삭제버튼 여부
}

function RankList({
  clubInfoArray,
  size = 'lg',
  onClickDeleteClub,
}: RankListProps) {
  const paddingClass = size === 'sm' ? 'p-md' : ''

  return (
    <div className={`${styles.container} ${styles[size]} ${paddingClass}`}>
      {clubInfoArray.map((clubInfo) => (
        <RankItem
          key={clubInfo.clubId}
          clubInfo={clubInfo}
          size={size}
          onClickDeleteClub={onClickDeleteClub}
        />
      ))}
    </div>
  )
}

export default RankList
