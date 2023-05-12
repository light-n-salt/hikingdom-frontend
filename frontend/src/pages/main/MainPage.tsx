import React, { useMemo } from 'react'
import styles from './MainPage.module.scss'
import { useNavigate } from 'react-router-dom'
import { getTodayMountains } from 'apis/services/mountains'
import { getRanking, getTodayClubMt } from 'apis/services/clubs'
import { MtInfo, TodayClubMt } from 'types/mt.interface'
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
import { toDate } from 'date-fns'

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

  // Api 연결되면 삭제할 데이터
  const todayClubMt = {
    clubId: 1,
  }

  return mtInfoArray && clubInfoArray && todayClubMt ? (
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
                navigate(`/club/${todayClubMt.clubId}/detail`)
              }}
            />
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
    totalDuration: '12:02',
    totalDistance: 123,
    participationRate: 87,
    ranking: 1,
  },
  {
    clubId: 2,
    clubName: '싸피 8기 1반',
    location: '서울특별시 강남구',
    totalMember: 10,
    totalDuration: '9:02',
    totalDistance: 100,
    participationRate: 100,
    ranking: 2,
  },
  {
    clubId: 3,
    clubName: '싸피 8기 전국캠퍼스 등산',
    location: '전국',
    totalMember: 15,
    totalDuration: '3:02',
    totalDistance: 73,
    participationRate: 67,
    ranking: 3,
  },
]
