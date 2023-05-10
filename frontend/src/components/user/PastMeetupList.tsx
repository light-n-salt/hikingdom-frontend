import React, { useState, useRef, useEffect } from 'react'
import styles from './PastMeetupList.module.scss'
import PastMeetupItem from './PastMeetupItem'
import Loading from 'components/common/Loading'

import { UserHiking } from 'types/user.interface'
import { useRecoilValue } from 'recoil'
import { userInfoState } from 'recoil/atoms'
import { getPastMeetups } from 'apis/services/users'
import useInfiniteScroll from 'hooks/useInfiniteScroll'

type PastMeetupProps = {
  hikingRecords: UserHiking[]
}

export default function PastMeetupList({ hikingRecords }: PastMeetupProps) {
  const userInfo = useRecoilValue(userInfoState)
  const [records, setRecords] = useState<UserHiking[]>(hikingRecords)
  const [isEnd, setIsEnd] = useState(false) // 무한스크롤 마지막 정보 여부
  const infiniteRef = useRef<HTMLDivElement>(null)

  function loadMore() {
    return getPastMeetups(
      userInfo.nickname,
      records.slice(-1)[0].hikingRecordId
    )
      .then((res) => {
        setRecords((records) => [...records, ...res.data.result.content])
        setIsEnd(!res.data.result.hasNext)
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore, isEnd })

  return (
    <div ref={infiniteRef} className={styles.scroll}>
      <div className={styles.list}>
        {records?.map((hiking) => (
          <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
        ))}
      </div>
      {isLoading && <Loading size="sm" />}
    </div>
  )
}
