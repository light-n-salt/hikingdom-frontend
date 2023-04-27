import RankList from 'components/common/RankList'
import React from 'react'

const clubInfoArray = [
    {
        clubId: 1,
        clubName: '산타마리아',
        location: '서울시 노원구',
        totalMember: 23,
        totalDuration: 123,
        totalDistance: 123,
        participationRate: 87,
        ranking: 2,
    },
    {
        clubId: 1,
        clubName: '산타마리아',
        location: '서울시 노원구',
        totalMember: 23,
        totalDuration: 123,
        totalDistance: 123,
        participationRate: 87,
        ranking: 2,
    },
]
function LoginPage() {
    return <RankList clubInfoArray={clubInfoArray} />
}

export default LoginPage
