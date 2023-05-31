import React, { useState } from 'react'
import styles from './PhotoModal.module.scss'
import toast from 'components/common/Toast'

import { Album } from 'types/club.interface'
import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { report } from 'apis/services/users'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { deleteAlbum } from 'apis/services/clubs'

import useUserQuery from 'hooks/useUserQuery'
import Modal from './Modal'
import IconText from './IconText'
import Image from 'components/common/Image'
import ConfirmModal from 'components/club/ConfirmModal'
import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'

type PhotoModalProps = {
  photo: Album
  setState: (isOpen: boolean) => void
}

function PhotoModal({ photo, setState }: PhotoModalProps) {
  const [isDeleteOpen, setIsDeleteOpen] = useState(false)
  const [isSirenOpen, setIsSirenOpen] = useState(false)
  const queryClient = useQueryClient()
  const { data: userInfo } = useUserQuery()

  const onClickDelete = useMutation(
    () => deleteAlbum(Number(userInfo?.clubId), photo.photoId),
    {
      onSuccess: () => {
        // 모임 앨범, 일정 앨범 query key 모두 무효화
        queryClient.invalidateQueries(['photos'])
        queryClient.invalidateQueries(['meetupPhotos'])
        setState(false)
        toast.addMessage('success', '사진이 삭제되었습니다')
      },
    }
  )

  const onClickReport = () => {
    report('ALBUM', photo.photoId)
      .then(() => {
        toast.addMessage('success', '신고가 완료됐습니다')
        setState(false)
      })
      .catch(() => {
        toast.addMessage('error', '이미 신고한 사진입니다')
        setState(false)
      })
  }

  return (
    <>
      {isDeleteOpen && (
        <Modal onClick={() => setIsDeleteOpen(false)}>
          <ConfirmModal
            title="사진을 삭제하시겠습니까?"
            buttonText="사진 삭제"
            onClickDelete={() => onClickDelete.mutate()}
            onClickCloseModal={() => setIsDeleteOpen(false)}
          />
        </Modal>
      )}
      {isSirenOpen && (
        <Modal onClick={() => setIsSirenOpen(false)}>
          <ConfirmModal
            title="사진을 신고하시겠습니까?"
            buttonText="사진 신고"
            onClickDelete={onClickReport}
            onClickCloseModal={() => setIsSirenOpen(false)}
          />
        </Modal>
      )}
      <div className={styles.container}>
        <div className={styles.user}>
          <Image size="xs" imgUrl={photo.profileUrl} />
          <span>{photo.nickname}</span>
        </div>
        {photo.isOwner ? (
          <div className={styles.siren} onClick={() => setIsDeleteOpen(true)}>
            <HiTrash /> 삭제하기
          </div>
        ) : (
          <div className={styles.siren} onClick={() => setIsSirenOpen(true)}>
            <HiLightBulb /> 신고하기
          </div>
        )}

        <div className={`${styles.date}`}>
          <IconText
            icon={<BiCalendarAlt className={styles.icon} />}
            text={photo.createdAt.split(' ')[0]}
            size="sm"
          />
          <IconText
            icon={<AiOutlineClockCircle className={styles.icon} />}
            text={photo.createdAt.split(' ')[1]}
            size="sm"
          />
        </div>
        <img src={photo.imgUrl} className={styles.img} />
      </div>
    </>
  )
}

export default PhotoModal
