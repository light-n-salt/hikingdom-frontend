import React, { useState, useRef } from 'react'
import styles from './MeetupAlbum.module.scss'
import { useParams } from 'react-router-dom'

import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import PhotoModal from 'components/common/PhotoModal'
import AlbumModal from 'components/meetup/AlbumModal'
import { Album } from 'types/club.interface'
import { updateMeetupAlbum, getMeeupAlbum } from 'apis/services/meetup'

type MeetupAlbumProps = {
  photoInfo: Album[]
}

function MeetupAlbum({ photoInfo }: MeetupAlbumProps) {
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }
  const [isOpen, setIsOpen] = useState(false) // 선택한 사진 모달 on/off
  const [photo, setPhoto] = useState<Album>() // 선택한 사진
  const [isAlbumOpen, setIsAlbumOpen] = useState(false) // 사진 업데이트 모달

  // 선택한 사진 모달에 띄우는 함수
  const onClickOpenModal = (photoId: number) => {
    const selectedPhoto = photoInfo.find((photo) => photo.photoId === photoId)
    if (selectedPhoto) {
      setPhoto(selectedPhoto)
      setIsOpen(true)
    }
  }

  return (
    <div className={styles.album}>
      {isAlbumOpen && (
        <Modal onClick={() => setIsAlbumOpen(false)}>
          <AlbumModal />
        </Modal>
      )}
      <div className={styles.titles}>
        <span className={styles.title}>추억</span>
        <Button
          text="추가"
          color="primary"
          size="xs"
          onClick={() => setIsAlbumOpen(true)}
        />
      </div>
      <div className={styles.photos}>
        {isOpen && (
          <Modal onClick={() => setIsOpen(false)}>
            {photo && <PhotoModal photo={photo} setState={setIsOpen} />}
          </Modal>
        )}
        {photoInfo.map((photo) => (
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
