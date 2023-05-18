import React, { useContext, useRef, useMemo } from 'react'
import useUserQuery from 'hooks/useUserQuery'
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
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

function ClubAlbumPage() {
  const { theme } = useContext(ThemeContext)
  const infiniteRef = useRef<HTMLDivElement>(null)

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const { data, isLoading, fetchNextPage, hasNextPage } =
    useInfiniteQuery<InfiniteAlbumInfo>({
      queryKey: ['photos'],
      queryFn: ({ pageParam = null }) => {
        return getClubAlbum(clubId || 0, pageParam, 21)
      },
      getNextPageParam: (lastPage) => {
        return lastPage.hasNext
          ? lastPage.content.slice(-1)[0].photoId
          : undefined
      },
      enabled: !!clubId,
    })

  const photoList = useMemo(() => {
    if (!data) return []
    return data.pages.flatMap((page) => page.content)
  }, [data])

  useInfiniteScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return (
    <div className={`page p-md ${theme}`}>
      <div ref={infiniteRef} className={styles.page}>
        {photoList && <AlbumList photoList={photoList} />}
        {isLoading && <Loading size="sm" />}
      </div>
    </div>
  )
}
export default ClubAlbumPage
