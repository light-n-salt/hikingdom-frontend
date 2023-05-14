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
  participationRate: number
  totalDuration: number
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
      <Info
        title="참여도(%)"
        content={`${participationRate}`}
        imgSrc={person}
        isBorder={true}
      />

      {/* 시간 */}
      <Info
        title="총 시간(h)"
        content={`${(totalDuration / 60).toFixed()}`}
        imgSrc={hourglass}
        isBorder={true}
      />

      {/* 거리 */}
      <Info
        title="총 거리(km)"
        content={convertToKm(totalDistance)}
        imgSrc={shoe}
        isBorder={true}
      />

      {/* 높이 */}
      <Info
        title="총 높이(km)"
        content={convertToKm(totalAlt)}
        imgSrc={hot_air_balloon}
        isBorder={false}
      />
    </div>
  )
}

type InfoProps = {
  title: string
  content: string
  imgSrc: string
  isBorder: boolean
}

function Info({ title, content, imgSrc, isBorder }: InfoProps) {
  const border = isBorder ? styles.border : ''

  return (
    <div className={`${styles.content} ${border}`}>
      <span className={styles.text}>{title}</span>
      <IconText imgSrc={imgSrc} text={content} size="md" isBold={true} />
    </div>
  )
}

export default ClubRecordInfo
