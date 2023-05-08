import React, { useContext } from 'react'
import { useParams } from 'react-router-dom'
import styles from './MtDetailPage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { getMountainInfo } from 'apis/services/mountains'
import { useQuery } from '@tanstack/react-query'
import { MtInfoDetail } from 'types/mt.interface'
import PageHeader from 'components/common/PageHeader'
import MtDetail from 'components/main/MtDetail'

function MtDetailPage() {
  // const { theme } = useContext(ThemeContext)

  const mountainId = useParams() as {
    mountainId: string
  }

  const { data: mtInfo } = useQuery<MtInfoDetail>(
    ['mountainInfo', { mountainId: mountainId }],
    () => getMountainInfo(Number(mountainId.mountainId))
  )

  console.log(mtInfo)

  return mtInfo ? (
    <div className={`page p-sm ${styles.detail}`}>
      <img src={mtInfo.imgUrl} className={styles.image} />
      <PageHeader title="" url="/main/search" color="light" />
      <MtDetail mtInfo={mtInfo} />
    </div>
  ) : (
    <div>loading...</div>
  )
}

export default MtDetailPage
