import React from 'react'
import { MtInfo } from 'types/mt.interface'
import MtItem from './MtItem'
import styles from './Mtlist.module.scss'

type MtListProps = {
    mtInfoArray: MtInfo[]
    size?: 'sm' | 'lg'
}

function MtList({ mtInfoArray, size = 'lg' }: MtListProps) {
    return (
        <div className={`${styles['mt-list']} ${styles[size]}`}>
            {mtInfoArray.map((mtInfo, index) => (
                <MtItem key={`MtItem-${index}`} mtInfo={mtInfo} size={size} />
            ))}
        </div>
    )
}

export default MtList
