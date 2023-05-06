import React from 'react'
import styles from './MainPage.module.scss'
import { useNavigate } from 'react-router-dom'
import cloud from 'assets/images/cloud.png'
import trophy from 'assets/images/trophy.png'
import mountain from 'assets/images/mountain.png'
import clubmountain from 'assets/images/clubmountain.png'
import MtList from 'components/common/MtList'
import IconText from 'components/common/IconText'
import RankList from 'components/common/RankList'

function MainPage() {
  const navigate = useNavigate()

  return (
    <div className={styles.container}>
      <div className={styles.section}>
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
      <div className={styles.section}>
        <IconText imgSrc={trophy} text="TOP3" size="md" isBold={true} />
        <div className={styles.scroll}>
          <RankList clubInfoArray={clubInfoArray} size="sm" />
        </div>
      </div>
      <div className={styles.section}>
        <IconText
          imgSrc={mountain}
          text="오늘의 모임 산"
          size="md"
          isBold={true}
        />
        <img
          src={clubmountain}
          onClick={() => {
            navigate(`/club/detail/1`)
          }}
        />
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
    clubId: 2,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 23,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 4,
  },
  {
    clubId: 3,
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
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
]
