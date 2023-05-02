import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

import styles from './MainPage.module.scss'

import MtList from 'components/common/MtList'
import SearchBar from 'components/common/SearchBar'
import RankList from 'components/common/RankList'
import IconText from 'components/common/IconText'

import mountain from 'assets/images/mountain.png'
import clubmountain from 'assets/images/clubmountain.png'
import cloud from 'assets/images/cloud.png'
import trophy from 'assets/images/trophy.png'

function MainPage() {
    const { theme } = useContext(ThemeContext)

    const [value, setValue] = useState('')

    return (
        <div className={`page ${theme} p-md ${styles['main']}`}>
            <SearchBar
                value={value}
                placeholder="산을 검색해보세요"
                onChangeText={setValue}
            />
            <div className={styles.container}>
                <IconText
                    imgSrc={cloud}
                    text="여기 등산 어때요"
                    size="md"
                    isBold={true}
                />
                <div className={styles.scroll}>
                    <MtList mtInfoArray={mtInfoArray} size="sm" />
                </div>
            </div>
            <div className={styles.container}>
                <IconText imgSrc={trophy} text="TOP3" size="md" isBold={true} />
                <div className={styles.scroll}>
                    <RankList clubInfoArray={clubInfoArray} size="sm" />
                </div>
            </div>
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
        ranking: 1,
    },
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
        ranking: 3,
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
]
