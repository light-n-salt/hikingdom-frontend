import React from 'react'
import styles from './PhotoModal.module.scss'
import { useParams } from 'react-router-dom'
import toast from 'components/common/Toast'

import { Album } from 'types/club.interface'
import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { report } from 'apis/services/users'
import { QueryClient, useMutation, useQueryClient } from '@tanstack/react-query'
import { deleteAlbum } from 'apis/services/clubs'

type PhotoModalProps = {
  photo: Album
  setState: (isOpen: boolean) => void
}

function PhotoModal({ photo, setState }: PhotoModalProps) {
  const queryClient = useQueryClient()
  const { clubId } = useParams() as {
    clubId: string
  }

  const onClickDelete = useMutation(
    () => deleteAlbum(parseInt(clubId), photo.photoId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['photos'])
        setState(false)
        toast.addMessage('success', '사진이 삭제되었습니다')
      },
    }
  )

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
        <HiTrash
          className={styles.delete}
          onClick={() => onClickDelete.mutate()}
        />
      )}
    </div>
  )
}

export default PhotoModal
