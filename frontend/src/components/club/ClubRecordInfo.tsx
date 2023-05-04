import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

import styles from './ClubRecordInfo.module.scss'
import IconText from 'components/common/IconText'

import { convertToKm } from 'utils/convertToKm'

import hot_air_balloon from 'assets/images/hot_air_balloon.png'
import hourglass from 'assets/images/hourglass.png'
import person from 'assets/images/person.png'
import shoe from 'assets/images/shoe.png'

type ClubRecordInfoProps = {
  participationRate: string
  totalDuration: string
  totalDistance: number
  totalAlt: number
}

function ClubRecordInfo({
  participationRate,
  totalDuration,
  totalDistance,
  totalAlt,
}: ClubRecordInfoProps) {
  const { theme } = useContext(ThemeContext)

  return (
    <div className={`content ${theme} ${styles.container}`}>
      {/* 참여도 */}
      <Info title="참여도(%)" content={participationRate} imgSrc={person} />

      {/* 시간 */}
      <Info title="총 시간(h)" content={totalDuration} imgSrc={hourglass} />

      {/* 거리 */}
      <Info
        title="총 거리(km)"
        content={convertToKm(totalDistance)}
        imgSrc={shoe}
      />

      {/* 높이 */}
      <Info
        title="총 높이(km)"
        content={convertToKm(totalAlt)}
        imgSrc={hot_air_balloon}
      />
    </div>
  )
}

type InfoProps = {
  title: string
  content: string
  imgSrc: string
}

function Info({ title, content, imgSrc }: InfoProps) {
  return (
    <div className={styles.content}>
      <span className={styles.text}>{title}</span>
      <IconText imgSrc={imgSrc} text={content} size="md" isBold={true} />
    </div>
  )
}

export default ClubRecordInfo
