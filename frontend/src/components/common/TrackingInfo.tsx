import React, { useEffect } from 'react'

import styles from './TrackingInfo.module.scss'
import Button from './Button'
import IconText from './IconText'
import { UserHikingDetail } from 'types/user.interface'

import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'

declare global {
    interface Window {
        kakao: any
    }
}

const { kakao } = window

interface TrackingInfoProps extends UserHikingDetail {
    title: string
    setIsOpen: (value: boolean) => void
    isProfile?: boolean
}

function TrackingInfo({
    title,
    setIsOpen,
    gpsRoute,
    startAt,
    totalDistance,
    maxAlt,
    totalDuration,
}: TrackingInfoProps) {
    useEffect(() => {
        const container = document.getElementById('map')
        const options = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),
            level: 3,
        }
        const map = new kakao.maps.Map(container, options)
    }, [])

    console.log('열렸다')
    console.log(title)

    return (
        <div className={styles.tracking}>
            <h2>{title}</h2>

            <div className={`${styles.container} ${styles.gap}`}>
                <IconText
                    icon={<BiCalendarAlt className={styles.icon} />}
                    text={startAt.split(' ')[0]}
                    size="sm"
                />
                <IconText
                    icon={<AiOutlineClockCircle className={styles.icon} />}
                    text={startAt.split(' ')[1]}
                    size="sm"
                />
            </div>

            {/* Kakaomap 띄우기 id를 map으로 해줘야 뜬다 */}
            <div id="map" className={styles.map}></div>

            {/* 트레킹 기록 */}
            <div className={styles.container}>
                {/* 거리 */}
                <div className={styles.content}>
                    <span className={styles.text}>거리</span>
                    <span className={styles.bold}>{totalDistance} km</span>
                </div>

                {/* 높이 */}
                <div className={styles.content}>
                    <span className={styles.text}>높이</span>
                    <span className={styles.bold}>{maxAlt} m</span>
                </div>

                {/* 시간 */}
                <div className={styles.content}>
                    <span className={styles.text}>시간</span>
                    <span className={styles.bold}>{totalDuration}</span>
                </div>
            </div>

            {/* 하단 버튼 */}
            <div className={`${styles.container} ${styles.gap}`}>
                <Button
                    text="종료"
                    size="lg"
                    color="primary"
                    onClick={() => console.log('종료')}
                />
            </div>
        </div>
    )
}

export default TrackingInfo
