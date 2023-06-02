import React, { useContext } from 'react'
import { useParams } from 'react-router-dom'
import styles from './MtDetailPage.module.scss'
import { useMountainInfoQuery } from 'apis/services/mountains'
import Loading from 'components/common/Loading'
import MtDetail from 'components/main/MtDetail'
import PageHeader from 'components/common/PageHeader'
import { ThemeContext } from 'styles/ThemeProvider'
import useRedirect from 'hooks/useRedirect'
import ErrorMessage from 'components/common/ErrorMessage'

function MtDetailPage() {
  const { theme } = useContext(ThemeContext)

  const { mountainId } = useParams() as {
    mountainId: string
  }

  const { arg1: parsedMountainId } = useRedirect(mountainId)

  const { isLoading, isError, data } = useMountainInfoQuery(parsedMountainId)

  if (isLoading) {
    return <Loading />
  }

  if (isError) {
    return <ErrorMessage message="정보를 불러올 수 없습니다." />
  }

  return (
    <div className={`page ${theme} p-sm ${styles.container}`}>
      <img src={data.imgUrl} className={styles.image} />
      <PageHeader title="" url="/main" color="light" />
      <MtDetail mtInfo={data} />
    </div>
  )
}

export default MtDetailPage
