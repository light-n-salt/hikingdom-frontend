import React, { useMemo } from 'react'
import { useParams } from 'react-router-dom'
import styles from './MtDetailPage.module.scss'
import { getMountainInfo } from 'apis/services/mountains'
import { useQuery } from '@tanstack/react-query'
import { MtInfoDetail } from 'types/mt.interface'
import Loading from 'components/common/Loading'
import MtDetail from 'components/main/MtDetail'
import PageHeader from 'components/common/PageHeader'
import { untilMidnight } from 'utils/untilMidnight'

function MtDetailPage() {
  const mountainId = useParams() as {
    mountainId: string
  }
  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const { data: mtInfo } = useQuery<MtInfoDetail>(
    ['mountainInfo', { mountainId: mountainId }],
    () => getMountainInfo(Number(mountainId.mountainId)),
    {
      cacheTime: queryTime,
      staleTime: queryTime,
    }
  )

  return mtInfo ? (
    <div className={`page p-sm ${styles.container}`}>
      <img src={mtInfo.imgUrl} className={styles.image} />
      <PageHeader title="" url="/main" color="light" />
      <MtDetail mtInfo={mtInfo} />
    </div>
  ) : (
    <Loading />
  )
}

export default MtDetailPage
