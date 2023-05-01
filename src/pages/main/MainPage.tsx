import MtList from 'components/common/MtList'
import SearchBar from 'components/common/SearchBar'
import RankList from 'components/common/RankList'
import React, { useContext, useState } from 'react'
import styles from './MainPage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

function MainPage() {
    const { theme } = useContext(ThemeContext)

    const [value, setValue] = useState('')

    return (
        <div className={`page ${theme} p-sm ${styles['search-mt']}`}>
            <SearchBar
                value={value}
                placeholder="산을 검색해보세요"
                onChangeText={setValue}
            />
            <RankList clubInfoArray={clubInfoArray} size="sm" />
            <MtList mtInfoArray={mtInfoArray} size="sm" />
        </div>
    )
}

export default MainPage

const clubInfoArray = [
    {
        clubId: 1,
        clubName: '산타마리아',
        location: '서울시 노원구',
        totalMember: 23,
        totalDuration: '12:02',
        totalDistance: 123,
        participationRate: 87,
        ranking: 2,
    },
    {
        clubId: 1,
        clubName: '산타마리아',
        location: '서울시 노원구',
        totalMember: 23,
        totalDuration: '12:02',
        totalDistance: 123,
        participationRate: 87,
        ranking: 5,
    },
]

const mtInfoArray = [
    {
        mountainId: 1,
        name: '도봉산',
        maxAlt: 123,
        address: '서울시 노원구',
        imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    },
    {
        mountainId: 1,
        name: '도봉산',
        maxAlt: 123,
        address: '서울시 노원구',
        imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
    // {
    //     mountainId: 1,
    //     name: '도봉산',
    //     maxAlt: 123,
    //     address: '서울시 노원구',
    //     imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
    // },
]
