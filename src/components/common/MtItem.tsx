import React from 'react'
import styles from './MtItem.module.scss'
import { useNavigate } from 'react-router-dom'
import marker from 'assets/images/marker.png'
import hotAirBalloon from 'assets/images/hot_air_balloon.png'
import IconText from 'components/common/IconText'
import { MtInfo } from 'types/mt.interface'

type MtItemProps = {
  mtInfo: MtInfo // 산 정보
  size?: 'sm' | 'lg' // 크기
}

function MtItem({ mtInfo, size = 'lg' }: MtItemProps) {
  const navigate = useNavigate()

  return (
    <div
      className={`${styles.container} ${styles[size]}`}
      onClick={() => navigate(`/mountain/${mtInfo.mountainId}/detail`)}
    >
      <img className={styles.img} src={mtInfo.imgUrl} />
      <h3>{mtInfo.name}</h3>
      <div className={styles.flexbox}>
        <IconText imgSrc={hotAirBalloon} text="높이" />
        {Math.floor(mtInfo.maxAlt)} m
      </div>
      <div className={styles.flexbox}>
        <IconText imgSrc={marker} text="위치" />
        {mtInfo.address.length > 8
          ? mtInfo.address.slice(0, 8) + '...'
          : mtInfo.address}
      </div>
    </div>
  )
}

export default MtItem
