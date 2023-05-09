import React, { useContext, useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubAlbumPage.module.scss'
import AlbumList from 'components/club/AlbumList'
import Loading from 'components/common/Loading'
import { Album } from 'types/club.interface'
import { getClubAlbum } from 'apis/services/clubs'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import { useInfiniteQuery } from '@tanstack/react-query'

type InfiniteAlbumInfo = {
  content: Album[]
  hasNext: boolean
  hasPrev: boolean
  numberOfElements: number
  pageSize: number
}

function ClubAlbumPage() {
  const { theme } = useContext(ThemeContext)
  const [photoList, setPhotoList] = useState<Album[]>([])
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 동작시킬 useRef
  const { clubId } = useParams() as { clubId: string }

  const { isLoading, fetchNextPage, hasNextPage } = useInfiniteQuery<any>({
    queryKey: ['photos'],
    queryFn: ({ pageParam = null }) => {
      getClubAlbum(parseInt(clubId), 21, pageParam)
    },
    getNextPageParam: (lastPage) => {
      return lastPage.data.result.hasNext
        ? lastPage.data.result.content.slice(-1)[0].photoId
        : undefined
    },
    onSuccess: (res) => {
      console.log('hi')
      setPhotoList((photoList) => [
        ...photoList,
        ...res.pages.slice(-1)[0].data.result.content,
      ])
    },
  })

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return (
    <div className={`page p-sm ${theme}`}>
      <div ref={infiniteRef} className={styles.page}>
        {photoList && <AlbumList photoList={photoList} />}
        {isLoading && <Loading size="sm" />}
      </div>
    </div>
  )
}
export default ClubAlbumPage
