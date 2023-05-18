import React, { useEffect, useMemo } from 'react'
import { useParams } from 'react-router'

import styles from './TrackingInfo.module.scss'

import { convertToKm } from 'utils/convertToKm'
import { convertToTime } from 'utils/convertToTime'
import { UserHikingDetail } from 'types/user.interface'
import { getTrackingInfo } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'
import { untilMidnight } from 'utils/untilMidnight'

import Loading from 'components/common/Loading'
import IconText from 'components/common/IconText'
import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'

type TrackingInfoProps = {
  hikingRecordId: number
  isandroid?: boolean
}

function TrackingInfo({
  hikingRecordId,
  isandroid = false,
}: TrackingInfoProps) {
  const { nickname } = useParams() as {
    nickname: string
  }

  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const { data: detailRecord } = useQuery<UserHikingDetail>(
    ['hikingInfo', { hikingRecordId: hikingRecordId }],
    () => getTrackingInfo(nickname, hikingRecordId),
    {
      cacheTime: queryTime,
      staleTime: queryTime,
    }
  )

  useEffect(() => {
    if (!detailRecord) {
      return
    }

    const { kakao } = window

    // 경로 정보
    const route = detailRecord.gpsRoute.gpsRoute

    const linePath: any = []
    let totalLat = 0
    let totalLng = 0

    route.forEach((path) => {
      linePath.push(new kakao.maps.LatLng(path.lat, path.lng)),
        (totalLat += path.lat),
        (totalLng += path.lng)
    })

    const startMarker = new kakao.maps.Marker({
      position: linePath[0],
    })

    const imageSrc =
      'https://tistory2.daumcdn.net/tistory/3056305/skin/images/map-marker-red.png' // 마커이미지의 주소입니다
    const imageSize = new kakao.maps.Size(35, 35) // 마커이미지의 크기입니다

    const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)

    const endMarker = new kakao.maps.Marker({
      position: linePath[route.length - 1],
      image: markerImage,
    })

    // 지도 그리는 정보
    const container = document.getElementById('map')
    const options = {
      center: new kakao.maps.LatLng(
        totalLat / route.length,
        totalLng / route.length
      ),
      level: 6,
    }
    const map = new kakao.maps.Map(container, options)

    // 선그리기
    const polyline: any = new kakao.maps.Polyline({
      path: linePath, // 선을 구성하는 좌표배열 입니다
      strokeWeight: 5, // 선의 두께 입니다
      strokeColor: '#000000', // 선의 색깔입니다
      strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      strokeStyle: 'solid', // 선의 스타일입니다
    })

    startMarker.setMap(map)
    endMarker.setMap(map)
    polyline.setMap(map)
  }, [detailRecord])

  const isPad = isandroid ? null : styles.pad

  return detailRecord ? (
    <div className={`${styles.tracking} ${isPad}`}>
      {!isandroid && <h2>{detailRecord.mountainName} 트래킹 기록</h2>}

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
        <Info title="높이" content={convertToKm(detailRecord.maxAlt) + 'km'} />

        {/* 시간 */}
        <Info
          title="시간"
          content={convertToTime(detailRecord.totalDuration)}
        />
      </div>
    </div>
  ) : (
    <Loading />
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
