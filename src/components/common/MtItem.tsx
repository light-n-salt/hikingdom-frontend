import React from 'react'
import { MtInfo } from 'types/mt.interface'
import IconText from './IconText'
import hotAirBalloon from 'assets/images/hot_air_balloon.png'
import marker from 'assets/images/marker.png'
import styles from './MtItem.module.scss'

type MtItemProps = {
    mtInfo: MtInfo
    size?: 'sm' | 'lg'
}

function MtItem({ mtInfo, size = 'lg' }: MtItemProps) {
    const maxAlt = mtInfo.maxAlt.toString() + 'm'

    return (
        <div className={styles['mt-item']}>
            <h3>{mtInfo.name}</h3>
            <div>
                <IconText imgSrc={hotAirBalloon} text="높이" />
                {maxAlt}
            </div>
            <div>
                <IconText imgSrc={marker} text="위치" />
                {mtInfo.address}
            </div>
            <img src={mtInfo.imgUrl} />
        </div>
    )
}

export default MtItem
