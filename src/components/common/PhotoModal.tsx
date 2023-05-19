import React from 'react'
import styles from './PhotoModal.module.scss'
import toast from 'components/common/Toast'

import { Album } from 'types/club.interface'
import { HiTrash, HiLightBulb } from 'react-icons/hi'

import { report } from 'apis/services/users'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { deleteAlbum } from 'apis/services/clubs'

import useUserQuery from 'hooks/useUserQuery'
import Image from 'components/common/Image'
import IconText from './IconText'
import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'

type PhotoModalProps = {
  photo: Album
  setState: (isOpen: boolean) => void
}

function PhotoModal({ photo, setState }: PhotoModalProps) {
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
    report('ALBUM', photo.photoId).then(() => {
      toast.addMessage('success', '신고가 완료됐습니다')
      setState(false)
    })
  }

  return (
    <div className={styles.container}>
      <div className={styles.user}>
        <Image size="xs" imgUrl={photo.profileUrl} />
        <span>{photo.nickname}</span>
      </div>
      {photo.isOwner ? (
        <div className={styles.siren} onClick={onClickReport}>
          <HiLightBulb /> 신고하기
        </div>
      ) : (
        <div className={styles.icon}>
          <HiTrash
            className={styles.delete}
            onClick={() => onClickDelete.mutate()}
          />
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
  )
}

export default PhotoModal
