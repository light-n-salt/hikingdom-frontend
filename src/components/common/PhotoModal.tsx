import React from 'react'
import styles from './PhotoModal.module.scss'

import { Album } from 'types/club.interface'
import { HiTrash, HiLightBulb } from 'react-icons/hi'

type PhotoModalProps = {
  photo: Album
}

function PhotoModal({ photo }: PhotoModalProps) {
  // Todo: delete and report photo
  const onClickDelete = () => {
    console.log('delete')
  }

  const onClickReport = () => {
    console.log('report')
  }

  return (
    <div className={styles.container}>
      <div className={styles.siren} onClick={onClickReport}>
        <HiLightBulb /> 신고하기
      </div>
      <img src={photo.imgUrl} className={styles.img} />
      <HiTrash className={styles.delete} onClick={onClickDelete} />
    </div>
  )
}

export default PhotoModal
