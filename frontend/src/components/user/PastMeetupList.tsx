import React, { useRef, useMemo } from 'react'
import styles from './PastMeetupList.module.scss'
import PastMeetupItem from './PastMeetupItem'
import Loading from 'components/common/Loading'
import { useParams } from 'react-router-dom'
import { UserHiking } from 'types/user.interface'

import { getPastMeetups } from 'apis/services/users'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { useInfiniteQuery } from '@tanstack/react-query'

type InfiniteMeetupInfo = {
  content: UserHiking[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

type PastMeetupListProps = {
  records: UserHiking[]
}

export default function PastMeetupList({ records }: PastMeetupListProps) {
  return (
    <div className={styles.scroll}>
      <div className={styles.list}>
        {records?.map((hiking) => (
          <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
        ))}
      </div>
    </div>
  )
}
