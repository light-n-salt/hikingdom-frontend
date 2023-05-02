import React, { useEffect } from 'react'

import styles from './TrackingInfo.module.scss'
import Button from './Button'
import { UserHikingDetail } from 'types/user.interface'

declare global {
    interface Window {
        kakao: any
    }
}

const { kakao } = window

function TrackingInfo({gpsRoute, totalDistance, maxAlt, totalDuration}: UserHikingDetail) {
    useEffect(() => {
        const container = document.getElementById('map')
        const options = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),
            level: 3,
        }
        const map = new kakao.maps.Map(container, options)
    }, [])
    return (
        <div className={styles.tracking}>
            <h2>하이킹을 종료하겠습니까?</h2>
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
                    <span className={styles.bold}>
                        {maxAlt} m
                    </span>
                </div>

                {/* 시간 */}
                <div className={styles.content}>
                    <span className={styles.text}>시간</span>
                    <span className={styles.bold}>
                        {totalDuration}
                    </span>
                </div>
            </div>
            <div className={styles.container}>
                <Button 
                    text="취소"
                    size='lg'
                    color='secondary'
                    onClick={() => console.log('취소')}
                />

                <Button 
                    text="종료"
                    size='lg'
                    color='primary'
                    onClick={() => console.log('종료')}
                />
            </div>
        </div>
    )
}

export default TrackingInfo
