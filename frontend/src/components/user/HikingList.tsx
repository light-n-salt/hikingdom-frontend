import React, { useRef, useMemo } from 'react'
import styles from './HikingList.module.scss'
import HikingItem from './HikingItem'
import Loading from 'components/common/Loading'
import { useParams } from 'react-router-dom'
import { HikingSimple } from 'types/user.interface'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { useInfiniteQuery } from '@tanstack/react-query'

type HikingListProps = {
  records: HikingSimple[]
}

function HikingList({ records }: HikingListProps) {
  return (
    <div className={styles.scroll}>
      <div className={styles.list}>
        {records?.map((hiking) => (
          <HikingItem key={hiking.hikingRecordId} hiking={hiking} />
        ))}
      </div>
    </div>
  )
}

export default HikingList
