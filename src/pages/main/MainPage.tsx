import React, { useMemo } from 'react'
import styles from './MainPage.module.scss'
import { useNavigate } from 'react-router-dom'
import { getTodayMountains } from 'apis/services/mountains'
import { getRanking, getTodayClubMt } from 'apis/services/clubs'
import { MtInfo } from 'types/mt.interface'
import { TodayClubMt } from 'types/club.interface'
import { ClubInfo } from 'types/club.interface'
import { useQuery } from '@tanstack/react-query'
import cloud from 'assets/images/cloud.png'
import trophy from 'assets/images/trophy.png'
import mountain from 'assets/images/mountain.png'
import MtList from 'components/common/MtList'
import IconText from 'components/common/IconText'
import RankList from 'components/common/RankList'
import Loading from 'components/common/Loading'
import ClubMoutain from 'components/club/ClubMoutain'
import { untilMidnight } from 'utils/untilMidnight'
import { getPosition } from 'utils/getPosition'

type InfiniteClubInfo = {
  content: ClubInfo[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

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

  const { data: clubInfoArray } = useQuery<InfiniteClubInfo>(
    ['clubRankTop3'],
    () => getRanking('', null, 3)
  )

  // const { data: todayClubMt } = useQuery<TodayClubMt>(
  //   ['todayClubMountain'],
  //   getTodayClubMt, {
  //     cacheTime: queryTime,
  //     staleTime: queryTime,
  //   }
  // )
  const assetArray = getPosition(assetInfo).arr

  // Api 연결되면 삭제할 데이터
  const todayClubMt = {
    clubId: 1,
  }

  return mtInfoArray && clubInfoArray && todayClubMt && assetArray ? (
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
              {/* <RankList clubInfoArray={clubInfoArray.content} size="sm" /> */}
              <RankList clubInfoArray={clubInfoArrayEx} size="sm" />
            </div>
          </div>
          <div
            className={`${styles.section} ${styles.height}`}
            onClick={() => {
              navigate(`/club/${todayClubMt.clubId}/detail`)
            }}
          >
            <IconText
              imgSrc={mountain}
              text="오늘의 모임 산"
              size="sm"
              isBold={true}
            />
            <ClubMoutain zoom={5} assetInfo={assetArray}/>
          </div>
        </div>
      )}
    </>
  ) : (
    <Loading />
  )
}

export default MainPage

const clubInfoArrayEx = [
  {
    clubId: 1,
    clubName: '산타마리아',
    location: '서울특별시 강남구',
    totalMember: 13,
    totalDuration: 722,
    totalDistance: 123,
    participationRate: 87,
    ranking: 1,
  },
  {
    clubId: 2,
    clubName: '싸피 8기 1반',
    location: '서울특별시 강남구',
    totalMember: 10,
    totalDuration: 542,
    totalDistance: 100,
    participationRate: 100,
    ranking: 2,
  },
  {
    clubId: 3,
    clubName: '싸피 8기 전국캠퍼스 등산',
    location: '전국',
    totalMember: 15,
    totalDuration: 183,
    totalDistance: 73,
    participationRate: 67,
    ranking: 3,
  },
]


const assetInfo = [
  {
      "mountainName": "",
      "meetupId": 0,
      "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/main.gltf",
      "row": 0,
      "column": 0,
  },
  {
      "mountainName": "감악산",
      "meetupId": 3,
      "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower5.gltf",
      "row": 0,
      "column": 0,
  },
  {
    "mountainName": "가리산",
    "meetupId": 1,
    "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower1.gltf",
    "row": 0,
    "column": 0,
},
{
    "mountainName": "가리왕산",
    "meetupId": 3,
    "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower2.gltf",
    "row": 0,
    "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower4.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "관악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower6.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리왕산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower7.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower8.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower9.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "관악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower10.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "도봉산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower11.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "대둔산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower12.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "덕숭산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower13.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower14.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "도봉산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower15.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리왕산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower16.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "명성산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower17.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower18.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower19.gltf",
  "row": 0,
  "column": 0,
  
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower20.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리왕산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower21.gltf",
  "row": 0,
  "column": 0,
  
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower22.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower23.gltf",
  "row": 0,
  "column": 0,
  
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower24.gltf",
  "row": 0,
  "column": 0,
},
{
      "mountainName": "가리산",
      "meetupId": 1,
      "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower25.gltf",
      "row": 0,
      "column": 0,
  },
  {
      "mountainName": "감악산",
      "meetupId": 3,
      "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower26.gltf",
      "row": 0,
      "column": 0,
  },
  {
    "mountainName": "가리산",
    "meetupId": 1,
    "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower27.gltf",
    "row": 0,
    "column": 0,
},
{
    "mountainName": "감악산",
    "meetupId": 3,
    "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower28.gltf",
    "row": 0,
    "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower29.gltf",
  "row": 0,
  "column": 0,
  
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower30.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower1.gltf",
  "row": 0,
  "column": 0,
  
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower5.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "가리산",
  "meetupId": 1,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower2.gltf",
  "row": 0,
  "column": 0,
},
{
  "mountainName": "감악산",
  "meetupId": 3,
  "assetUrl": "https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower10.gltf",
  "row": 0,
  "column": 0,
},
]