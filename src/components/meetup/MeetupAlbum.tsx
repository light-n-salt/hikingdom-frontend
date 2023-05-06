import React, { useState } from 'react'
import styles from './MeetupAlbum.module.scss'

import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import PhotoModal from 'components/common/PhotoModal'
import { Album } from 'types/club.interface'

type MeetupAlbumProps = {
  photoInfo: Album[]
}

function MeetupAlbum({ photoInfo }: MeetupAlbumProps) {
  const [isOpen, setIsOpen] = useState(false) // 모달 on/off
  const [photo, setPhoto] = useState<Album | undefined>(undefined) // 선택한 사진

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
      <div className={styles.titles}>
        <span className={styles.title}>추억</span>
        <Button text="추가" color="primary" size="xs" />
      </div>
      <div className={styles.photos}>
        {isOpen && (
          <Modal onClick={() => setIsOpen(false)}>
            {photo && <PhotoModal photo={photo} />}
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
