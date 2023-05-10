import React, { useContext, useState, useRef } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ImageUpdateForm.module.scss'
import { useNavigate } from 'react-router'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import IconButton from 'components/common/IconButton'
import toast from 'components/common/Toast'
import { TbCameraPlus } from 'react-icons/tb'

import { getUserInfo, updateProfile } from 'apis/services/users'
import { useMutation } from '@tanstack/react-query'

import { useRecoilState } from 'recoil'
import { userInfoState } from 'recoil/atoms'

function ImageUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const [userInfo, setUserInfo] = useRecoilState(userInfoState)
  const [imgUrl, setImgUrl] = useState(userInfo.profileUrl)
  const imgRef = useRef<any>(null)
  const navigate = useNavigate()

  // 업데이트 mutation
  const updateImg = useMutation(updateProfile, {
    onSuccess: () => {
      toast.addMessage('success', '프로필이 변경되었습니다')
      getUserInfo(setUserInfo)
      navigate(`/profile/${userInfo.nickname}`)
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
          onClick={() => imgRef.current.click()}
        />
      )}
    </div>
  ) : (
    <div>Loading...</div>
  )
}

export default ImageUpdateForm
