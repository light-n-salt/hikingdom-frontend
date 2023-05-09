import React, { useMemo } from 'react'
import styles from './MainPage.module.scss'
import { useNavigate } from 'react-router-dom'
import { getTodayMountains } from 'apis/services/mountains'
import { getRanking } from 'apis/services/clubs'
import { MtInfo } from 'types/mt.interface'
import { ClubInfo } from 'types/club.interface'
import { useQuery } from '@tanstack/react-query'
import cloud from 'assets/images/cloud.png'
import trophy from 'assets/images/trophy.png'
import mountain from 'assets/images/mountain.png'
import clubmountain from 'assets/images/clubmountain.png'
import MtList from 'components/common/MtList'
import IconText from 'components/common/IconText'
import RankList from 'components/common/RankList'
import Loading from 'components/common/Loading'
import { untilMidnight } from 'utils/untilMidnight'

function MainPage() {
  const navigate = useNavigate()
  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  // data: data의 변수명 지정
  const {
    data: mtInfoArray,
    isLoading,
    isError,
  } = useQuery<MtInfo[]>(['todayMountain'], getTodayMountains, {
    cacheTime: queryTime,
    staleTime: queryTime,
  })

  // const { data: clubInfoArray } = useQuery<ClubInfo[]>(['clubRankTop3'], () =>
  //   getRanking('', null, 3).then((res) => res.data.result)
  // )

  return (
    <>
      {isLoading || isError ? (
        <Loading />
      ) : (
        <div className={styles.container}>
          <div className={styles.section}>
            <IconText
              imgSrc={cloud}
              text="여기 등산 어때요"
              size="sm"
              isBold={true}
            />
            <div className={styles.scroll}>
              <MtList mtInfoArray={mtInfoArray} size="sm" />
            </div>
          </div>
          <div className={styles.section}>
            <IconText imgSrc={trophy} text="TOP3" size="sm" isBold={true} />
            <div className={styles.scroll}>
              <RankList clubInfoArray={clubInfoArrayEx} size="sm" />
            </div>
          </div>
          <div className={styles.section}>
            <IconText
              imgSrc={mountain}
              text="오늘의 모임 산"
              size="sm"
              isBold={true}
            />
            <img
              src={clubmountain}
              onClick={() => {
                navigate(`/club/1/detail`)
              }}
            />
          </div>
        </div>
      )}
    </>
  )
}

export default MainPage

const clubInfoArrayEx = [
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울시 노원구',
    totalMember: 13,
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 1,
  },
  {
    clubId: 2,
    clubName: '산타마리오',
    location: '서울시 강남구',
    totalMember: 10,
    totalDuration: '9:02',
    totalDistance: 100,
    participationRate: 100,
    ranking: 2,
  },
  {
    clubId: 3,
    clubName: '산타지마',
    location: '서울시 동작구',
    totalMember: 15,
    totalDuration: '3:02',
    totalDistance: 73,
    participationRate: 67,
    ranking: 3,
  },
]

const mtInfoArrayEx = [
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 2,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 3,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 4,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 5,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
]
