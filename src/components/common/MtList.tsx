import React from 'react'
import styles from './Mtlist.module.scss'
import MtItem from 'components/common/MtItem'
import { MtInfo } from 'types/mt.interface'

type MtListProps = {
  mtInfoArray: MtInfo[] // 산 정보 배열
  size?: 'sm' | 'lg' // 사이즈
}

function MtList({ mtInfoArray, size = 'lg' }: MtListProps) {
  return (
    <div className={`${styles.container} ${styles[size]}`}>
      {mtInfoArray.map((mtInfo, index) => (
        <MtItem key={`mtinfo-item-${index}`} mtInfo={mtInfo} size={size} />
      ))}
    </div>
  )
}

export default MtList
