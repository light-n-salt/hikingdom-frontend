import React, { useState, useRef, useMemo } from 'react'
import styles from './MeetupAlbum.module.scss'
import { useParams } from 'react-router-dom'

import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import PhotoModal from 'components/common/PhotoModal'
import AlbumModal from 'components/meetup/AlbumModal'
import { Album } from 'types/club.interface'
import { getMeetupAlbum } from 'apis/services/meetup'

import useInfiniteVerticalScroll from 'hooks/useInfiniteVerticalScroll'
import { useInfiniteQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'

type MeetupAlbum = {
  join: boolean
}

type InfiniteAlbumInfo = {
  content: Album[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

function MeetupAlbum({ join }: MeetupAlbum) {
  const { meetupId } = useParams() as {
    meetupId: string
  }

  const { data: userInfo } = useUserQuery()
  const [isOpen, setIsOpen] = useState(false) // 선택한 사진 모달 on/off
  const [photo, setPhoto] = useState<Album>() // 선택한 사진
  const [isAlbumOpen, setIsAlbumOpen] = useState(false) // 사진 업데이트 모달
  const infiniteRef = useRef<HTMLDivElement>(null)

  const { data, fetchNextPage, hasNextPage } =
    useInfiniteQuery<InfiniteAlbumInfo>({
      queryKey: ['meetupPhotos'],
      queryFn: ({ pageParam = null }) => {
        return getMeetupAlbum(
          Number(userInfo?.clubId),
          Number(meetupId),
          pageParam,
          5
        )
      },
      getNextPageParam: (lastPage) => {
        return lastPage.hasNext
          ? lastPage.content.slice(-1)[0].photoId
          : undefined
      },
      enabled: !!userInfo,
    })

  const photoInfo = useMemo(() => {
    if (!data) return []
    return data.pages.flatMap((page) => page.content)
  }, [data])

  useInfiniteVerticalScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  // 선택한 사진 모달에 띄우는 함수
  const onClickOpenModal = (photoId: number) => {
    const selectedPhoto = photoInfo?.find((photo) => photo.photoId === photoId)
    if (selectedPhoto) {
      setPhoto(selectedPhoto)
      setIsOpen(true)
    }
  }

  return (
    <div className={styles.album}>
      {/* 사진 등록 모달 */}
      {isAlbumOpen && (
        <Modal onClick={() => setIsAlbumOpen(false)}>
          <AlbumModal
            clubId={userInfo?.clubId}
            setIsOpen={() => setIsAlbumOpen(false)}
          />
        </Modal>
      )}
      {/* 사진 상세보기 모달 */}
      {isOpen && (
        <Modal onClick={() => setIsOpen}>
          {photo && <PhotoModal photo={photo} setState={setIsOpen} />}
        </Modal>
      )}
      <div className={styles.titles}>
        <span className={styles.title}>추억</span>
        {join && (
          <Button
            text="추가"
            color="primary"
            size="xs"
            onClick={() => setIsAlbumOpen(true)}
          />
        )}
      </div>
      <div ref={infiniteRef} className={styles.photos}>
        {photoInfo?.map((photo) => (
          <img
            key={photo.photoId}
            src={photo.imgUrl}
            className={styles.photo}
            onClick={() => onClickOpenModal(photo.photoId)}
          />
        ))}
      </div>
    </div>
  )
}

export default MeetupAlbum
