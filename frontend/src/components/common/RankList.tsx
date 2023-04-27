import React from 'react'
import { ClubInfo } from 'types/club.interface'
import RankItem from './RankItem'
import styles from './RankList.module.scss'

type RankListProps = {
    clubInfoArray: ClubInfo[]
    size?: 'sm' | 'lg'
}

function RankList({ clubInfoArray, size = 'lg' }: RankListProps) {
    return (
        <div className={`${styles.ranklist} ${styles[size]}`}>
            {clubInfoArray.map((clubInfo, index) => (
                <RankItem
                    key={`rank-item-${index}`}
                    clubInfo={clubInfo}
                    size={size}
                />
            ))}
        </div>
    )
}

export default RankList
