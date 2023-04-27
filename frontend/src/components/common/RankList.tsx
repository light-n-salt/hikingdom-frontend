import React from 'react'
import { ClubInfo } from 'types/club.interface'
import RankItem from './RankItem'

type RankListProps = {
    clubInfoArray: ClubInfo[]
    size?: 'sm' | 'lg'
}

function RankList({ clubInfoArray, size = 'lg' }: RankListProps) {
    return (
        <div>
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
