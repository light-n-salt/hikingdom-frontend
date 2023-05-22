import React, { useState, useRef } from 'react'
import styles from './AlbumModal.module.scss'
import { updateMeetupAlbum } from 'apis/services/meetup'
import { useParams } from 'react-router-dom'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'

const MAX_FILE_SIZE = 10 * 1024 * 1024 // 10MB 이하 사진만 업로드

type AlbumModalProps = {
  clubId: number | undefined
  setIsOpen: () => void
}

function AlbumModal({ setIsOpen, clubId }: AlbumModalProps) {
  const { meetupId } = useParams() as { meetupId: string }
  const [files, setFiles] = useState<string[] | undefined>()
  const albumRef = useRef<HTMLInputElement>(null)
  const queryClient = useQueryClient()

  // 선택한 사진 모달에 띄우기
  const onChange = () => {
    const fileList = albumRef.current?.files
    const urlList = []

    if (fileList) {
      for (let i = 0; i < fileList.length; i++) {
        const imgUrl = URL.createObjectURL(fileList[i])
        urlList.push(imgUrl)
      }
      setFiles(urlList)
    }
  }

  // 사진 업로드하기
  const { mutate } = useMutation(
    (formData: FormData) =>
      updateMeetupAlbum(Number(clubId), Number(meetupId), formData),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['meetupPhotos'])
        toast.addMessage('success', '사진이 추가되었습니다')
        setIsOpen()
      },
    }
  )

  const updateFiles = () => {
    if (!albumRef.current?.files) return

    const fileList = albumRef.current.files
    const formData = new FormData()

    // 선택한 사진이 없을 때
    if (!fileList.length) {
      toast.addMessage('error', '사진을 선택해주세요')
      return
    }

    // 선택한 사진이 있을 때
    if (fileList.length) {
      for (let i = 0; i < fileList.length; i++) {
        // 용량 초과시 return
        if (fileList[i].size > MAX_FILE_SIZE) {
          toast.addMessage('error', '사진은 10MB이하로 올릴 수 있습니다')
          return
        }
        formData.append('photos', fileList[i])
      }
    }

    mutate(formData)
  }
  return (
    <div className={styles.modal}>
      {!!files && (
        <div className={styles.files}>
          {files.map((file, id) => (
            <img key={id} src={file} className={styles.img} />
          ))}
        </div>
      )}
      <div className={styles.btns}>
        <Button
          text="사진 선택"
          color="secondary"
          size="sm"
          onClick={() => albumRef.current && albumRef.current.click()}
        />
        {files && (
          <Button
            text="사진 올리기"
            color="secondary"
            size="sm"
            onClick={updateFiles}
          />
        )}
      </div>
      <input
        type="file"
        multiple
        accept="image/jpg,image/png,image/jpeg"
        onChange={onChange}
        ref={albumRef}
        style={{ display: 'none' }}
      />
    </div>
  )
}
export default AlbumModal
