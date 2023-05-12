import React, { useContext, useState, useRef, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ImageUpdateForm.module.scss'
import { useNavigate } from 'react-router'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import IconButton from 'components/common/IconButton'
import toast from 'components/common/Toast'
import Loading from 'components/common/Loading'
import { TbCameraPlus } from 'react-icons/tb'

import { updateProfile } from 'apis/services/users'
import { useMutation, useQueryClient } from '@tanstack/react-query'

import useUserQuery from 'hooks/useUserQuery'

function ImageUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const [imgUrl, setImgUrl] = useState('')
  const imgRef = useRef<HTMLInputElement>(null)
  const navigate = useNavigate()
  const queryClient = useQueryClient()

  // 유저 정보
  const { data: userInfo } = useUserQuery()

  // 처음 가져오는 이미지는 유저 정보에서
  useEffect(() => {
    if (!userInfo) return
    setImgUrl(userInfo.profileUrl)
  }, [])

  // 업데이트 mutation
  const updateImg = useMutation(updateProfile, {
    onSuccess: () => {
      toast.addMessage('success', '프로필이 변경되었습니다')
      queryClient.invalidateQueries(['userProfile'])
      navigate(`/profile/${userInfo?.nickname}`)
    },
  })

  // 프로필 변경 반영
  const onChange = () => {
    const reader = new FileReader()
    const file = imgRef.current?.files?.[0]

    if (file) {
      reader.readAsDataURL(file) // 이미지 URL
      reader.onloadend = () => {
        setImgUrl(reader.result as string)
      }
    }
  }

  // 프로필 업데이트
  const onClickUpdate = () => {
    if (!imgRef.current?.files) return

    const file = imgRef.current?.files?.[0]
    const formData = new FormData()
    formData.append('image', file)
    if (!file) {
      toast.addMessage('error', '프로필을 선택해주세요')
      return
    }
    updateImg.mutate(formData)
  }

  return userInfo ? (
    <div className={`content ${theme} ${styles.img}`}>
      <Button
        text="사진 변경"
        color="secondary"
        size="sm"
        onClick={onClickUpdate}
      />
      <Image imgUrl={imgUrl} size="lg" />
      <input
        type="file"
        accept="image/jpg,image/png,image/jpeg"
        onChange={onChange}
        ref={imgRef}
        style={{ display: 'none' }}
      />
      {imgRef && (
        <IconButton
          icon={<TbCameraPlus />}
          size="sm"
          color="gray"
          onClick={() => imgRef.current && imgRef.current.click()}
        />
      )}
    </div>
  ) : (
    <div>
      <Loading />
    </div>
  )
}

export default ImageUpdateForm
