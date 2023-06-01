import React, { useContext, useRef, useMemo } from 'react'
import useUserQuery from 'hooks/useUserQuery'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubAlbumPage.module.scss'
import AlbumList from 'components/club/AlbumList'
import Loading from 'components/common/Loading'
import { useClubAlbum } from 'apis/services/clubs'
import useInfiniteScroll from 'hooks/useInfiniteScroll'

function ClubAlbumPage() {
  const { theme } = useContext(ThemeContext)
  const infiniteRef = useRef<HTMLDivElement>(null)

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const { data, isLoading, fetchNextPage, hasNextPage } = useClubAlbum(
    clubId || 0
  )

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
      {photoList.length === 0 ? (
        <div className={styles.blank}>등록된 사진이 없습니다</div>
      ) : (
        <div ref={infiniteRef} className={styles.page}>
          <AlbumList photoList={photoList} />
          {isLoading && <Loading size="sm" />}
        </div>
      )}
    </div>
  )
}
export default ClubAlbumPage
