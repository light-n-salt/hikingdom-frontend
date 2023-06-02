import React, { useContext, useMemo, useRef } from 'react'
import styles from './AlarmPage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { Alarm } from 'types/user.interface'

import PageHeader from 'components/common/PageHeader'
import AlarmList from 'components/user/AlarmList'

import useUserQuery from 'hooks/useUserQuery'
import { useQuery } from '@tanstack/react-query'
import { useInfiniteAlarmQuery } from 'apis/services/users'
import Loading from 'components/common/Loading'
import ErrorMessage from 'components/common/ErrorMessage'
import useInfiniteScroll from 'hooks/useInfiniteScroll'

function AlarmPage() {
  const { theme } = useContext(ThemeContext)
  const infiniteRef = useRef<HTMLDivElement>(null)

  const {
    data,
    isLoading,
    isError,
    isFetchingNextPage,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteAlarmQuery()

  const alarmList = useMemo(() => {
    return data ? data.pages.flatMap((page) => page.content) : []
  }, [data])

  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  if (isLoading) {
    return <Loading />
  }

  if (isError) {
    return <ErrorMessage message="등산기록을 불러오는데 실패했습니다" />
  }

  return (
    <div className={`page p-md ${theme}`}>
      <PageHeader color="primary" title="알림 내역" />
      {alarmList.length ? (
        <div ref={infiniteRef} className={styles.list}>
          <AlarmList alarmList={alarmList} />
        </div>
      ) : (
        <p className={styles.blank}>알람 내역이 없습니다</p>
      )}
    </div>
  )
}

export default AlarmPage
