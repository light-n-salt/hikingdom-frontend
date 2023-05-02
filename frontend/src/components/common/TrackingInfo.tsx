import React, { useEffect } from 'react'

import styles from './TrackingInfo.module.scss'

declare global {
    interface Window {
        kakao: any
    }
}

const { kakao } = window

function TrackingInfo() {
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
        </div>
    )
}

export default TrackingInfo
