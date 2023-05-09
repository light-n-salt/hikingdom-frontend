import React from 'react'
import styles from './PhotoModal.module.scss'

import toast from 'components/common/Toast'

import { Album } from 'types/club.interface'
import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { report } from 'apis/services/users'

type PhotoModalProps = {
  photo: Album
  setState: (isOpen: boolean) => void
}

function PhotoModal({ photo, setState }: PhotoModalProps) {
  const onClickDelete = () => {
    console.log('delete')
  }

  const onClickReport = () => {
    report('ALBUM', photo.photoId).then(() => {
      toast.addMessage('success', '신고가 완료됐습니다')
      setState(false)
    })
  }

  return (
    <div className={styles.container}>
      <div className={styles.siren} onClick={onClickReport}>
        <HiLightBulb /> 신고하기
      </div>
      <img src={photo.imgUrl} className={styles.img} />
      {photo.isOwner && (
        <HiTrash className={styles.delete} onClick={onClickDelete} />
      )}
    </div>
  )
}

export default PhotoModal
