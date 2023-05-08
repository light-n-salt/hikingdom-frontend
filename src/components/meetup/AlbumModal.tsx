import React, { useState, useRef } from 'react'
import styles from './AlbumModal.module.scss'
import { updateMeetupAlbum, getMeeupAlbum } from 'apis/services/meetup'
import { useParams } from 'react-router-dom'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'

const MAX_FILE_SIZE = 1024 * 1024 // 1MB 이하 사진만 업로드

function AlbumModal() {
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }
  const [files, setFiles] = useState<string[] | undefined>()

  const albumRef = useRef<any>(null)

  // 프로필 변경 반영
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

  const updateFiles = () => {
    const fileList = albumRef.current?.files
    const formData = new FormData()
    if (!fileList.length) {
      toast.addMessage('error', '사진을 선택해주세요')
      return
    }
    if (fileList.length) {
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i].size > MAX_FILE_SIZE) {
          toast.addMessage('error', '사진은 1MB이하로 올릴 수 있습니다')
          return
        }
        formData.append('photo', fileList[i])
      }
    }

    updateMeetupAlbum(parseInt(clubId), parseInt(meetupId), formData).then(
      () => {
        console.log('hi')
        // getMeeupAlbum(parseInt(clubId), parseInt(meetupId)).then((res) =>
        //   console.log(res.data.result, '성공이지렁')
        // )
      }
    )
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
          onClick={() => albumRef.current.click()}
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
