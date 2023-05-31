import React, { useMemo } from 'react'
import styles from './MainPage.module.scss'
import { useNavigate } from 'react-router-dom'
import { getTodayMountains } from 'apis/services/mountains'
import { getTodayClubMt, useclubRankTop3Query } from 'apis/services/clubs'
import { MtInfo } from 'types/mt.interface'
import { TodayClubMt } from 'types/club.interface'
import { useQuery } from '@tanstack/react-query'
import cloud from 'assets/images/cloud.png'
import trophy from 'assets/images/trophy.png'
import mountain from 'assets/images/mountain.png'
import MtList from 'components/common/MtList'
import IconText from 'components/common/IconText'
import RankList from 'components/common/RankList'
import Loading from 'components/common/Loading'
import ClubMountain from 'components/club/ClubMountain'
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

  const {
    isLoading: isClubRankTop3Loading,
    isError: isClubRankTop3Error,
    data: clubRankTop3,
    isSuccess,
  } = useclubRankTop3Query()

  const { data: todayClubMt } = useQuery<TodayClubMt>(
    ['todayClubMountain'],
    getTodayClubMt,
    {
      cacheTime: queryTime,
      staleTime: queryTime,
    }
  )

  return mtInfoArray && clubRankTop3 && todayClubMt ? (
    <>
      {isLoading || isError ? (
        <Loading />
      ) : (
        <div className={styles.container}>
          <div className={styles.section}>
            <div className="p-md">
              <IconText
                imgSrc={cloud}
                text="여기 등산 어때요"
                size="md"
                isBold={true}
              />
            </div>
            <div className={styles.scroll}>
              <MtList mtInfoArray={mtInfoArray} size="sm" />
            </div>
          </div>
          <div className={styles.section}>
            <div className="p-md">
              <IconText imgSrc={trophy} text="TOP3" size="md" isBold={true} />
            </div>
            <div className={styles.scroll}>
              <RankList clubInfoArray={clubRankTop3.content} size="sm" />
            </div>
          </div>
          <div
            className={`${styles.section} ${styles.height}`}
            onClick={() => {
              navigate(`/club/${todayClubMt.clubId}/detail`)
            }}
          >
            <div className="p-md">
              <IconText
                imgSrc={mountain}
                text="오늘의 모임 산"
                size="md"
                isBold={true}
              />
            </div>
            <ClubMountain zoom={3.5} assetInfo={todayClubMt.assets} />
          </div>
        </div>
      )}
    </>
  ) : (
    <Loading />
  )
}

export default MainPage
