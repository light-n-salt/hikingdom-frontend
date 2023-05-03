import React, { useEffect } from 'react'

import styles from './TrackingInfo.module.scss'
import Button from './Button'
import IconText from './IconText'

import { convertToKm } from 'utils/convertToKm'
import { UserHikingDetail } from 'types/user.interface'

import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'

declare global {
  interface Window {
    kakao: any
  }
}

const { kakao } = window

type TrackingInfoProps = {
  hikingRecordId: number
  onClickCloseModal: any
}

function TrackingInfo({
  hikingRecordId,
  onClickCloseModal,
}: TrackingInfoProps) {
  useEffect(() => {
    // todo: API 연결

    const route = [
      { Lat: 37.498085, lng: 127.027545 },
      { Lat: 37.501394, lng: 127.038489 },
      { Lat: 37.504493, lng: 127.048877 },
    ]

    const container = document.getElementById('map')
    const options = {
      center: new kakao.maps.LatLng(37.501394, 127.038489),
      level: 6,
    }
    const map = new kakao.maps.Map(container, options)

    // 선그리기 코드 (빼줄지 고민... => 지도가 이상한데?)

    // const linePath: any = []

    // route.map((path, index) =>
    //   linePath.push(new kakao.maps.LatLng(path.Lat, path.lng))
    // )

    // const polyline: any = new kakao.maps.Polyline({
    //   path: linePath, // 선을 구성하는 좌표배열 입니다
    //   strokeWeight: 5, // 선의 두께 입니다
    //   strokeColor: '#000000', // 선의 색깔입니다
    //   strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //   strokeStyle: 'solid', // 선의 스타일입니다
    // })

    // polyline.setMap(map)
  }, [])

  const detailRecord: UserHikingDetail = {
    gpsRoute: '위도경도',
    mountainName: '도봉산',
    startAt: 'YYYY.MM.DD HH:mm:ss',
    totalDuration: '23:00',
    totalDistance: 402,
    maxAlt: 203,
  }

  return (
    <div className={styles.tracking}>
      <h2>{detailRecord.mountainName} 트래킹 기록</h2>

      <div className={`${styles.container} ${styles.gap}`}>
        <IconText
          icon={<BiCalendarAlt className={styles.icon} />}
          text={detailRecord.startAt.split(' ')[0]}
          size="sm"
        />
        <IconText
          icon={<AiOutlineClockCircle className={styles.icon} />}
          text={detailRecord.startAt.split(' ')[1]}
          size="sm"
        />
      </div>

      {/* Kakaomap 띄우기 id를 map으로 해줘야 뜬다 */}
      <div id="map" className={styles.map}></div>

      {/* 트레킹 기록 */}
      <div className={styles.container}>
        {/* 거리 */}
        <Info
          title="거리"
          content={convertToKm(detailRecord.totalDistance) + 'km'}
        />

        {/* 높이 */}
        <Info
          title="높이"
          content={convertToKm(detailRecord.totalDistance) + 'km'}
        />

        {/* 시간 */}
        <Info title="시간" content={detailRecord.totalDuration} />
      </div>

      {/* 하단 버튼 */}
      <div className={`${styles.container} ${styles.gap}`}>
        <Button
          text="종료"
          size="lg"
          color="primary"
          onClick={onClickCloseModal}
        />
      </div>
    </div>
  )
}

type InfoProps = {
  title: string
  content: string
}

function Info({ title, content }: InfoProps) {
  return (
    <div className={styles.content}>
      <span className={styles.text}>{title}</span>
      <span className={styles.bold}>{content}</span>
    </div>
  )
}

export default TrackingInfo
