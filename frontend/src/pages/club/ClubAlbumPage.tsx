import React, { useContext, useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubAlbumPage.module.scss'
import AlbumList from 'components/club/AlbumList'
import Loading from 'components/common/Loading'
import { Album } from 'types/club.interface'
import { getClubAlbum } from 'apis/services/clubs'
import useInfiniteScroll from 'hooks/useInfiniteScroll'

function ClubAlbumPage() {
  const { theme } = useContext(ThemeContext)
  const [photoList, setPhotoList] = useState<Album[]>([])
  const [isEnd, setIsEnd] = useState(false) // 무한스크롤 마지막 정보 여부
  const infiniteRef = useRef<HTMLDivElement>(null) // 무한 스크롤 동작시킬 useRef
  const { clubId } = useParams() as { clubId: string }

  useEffect(() => {
    getClubAlbum(parseInt(clubId), 21).then((res) => {
      setPhotoList(res.data.result.content)
      setIsEnd(!res.data.result.hasNext)
    })
  }, [])

  function loadMore() {
    return getClubAlbum(parseInt(clubId), 21, photoList.slice(-1)[0].photoId)
      .then((res) => {
        setPhotoList((photoList) => [...photoList, ...res.data.result.content])
        setIsEnd(!res.data.result.hasNext)
      })
      .catch(() => {})
  }

  // 무한스크롤 커스텀 훅(동작 요소, 동작 함수)
  const { isLoading } = useInfiniteScroll({ ref: infiniteRef, loadMore, isEnd })

  return (
    <div className={`page p-sm ${theme}`}>
      <div ref={infiniteRef} className={styles.page}>
        <AlbumList photoList={photoList} />
        {isLoading && <Loading size="sm" />}
      </div>
    </div>
  )
}
export default ClubAlbumPage
