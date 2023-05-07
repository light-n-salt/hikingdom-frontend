import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ImageUpdateForm.module.scss'

import Image from 'components/common/Image'

import IconButton from 'components/common/IconButton'
import { TbCameraPlus } from 'react-icons/tb'

import { User } from 'types/user.interface'
import { getMember } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'

import { useRecoilValue } from 'recoil'
import { userInfoState } from 'recoil/atoms'

function ImageUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const userInfo = useRecoilValue(userInfoState)

  // Todo: 이미지 업데이트
  const onClickUpdateImg = () => {
    console.log('프로필 수정')
  }

  return userInfo ? (
    <div className={`content ${theme} ${styles.img}`}>
      <Image imgUrl={userInfo.profileUrl} size="lg" />
      <IconButton
        icon={<TbCameraPlus />}
        size="sm"
        color="gray"
        onClick={onClickUpdateImg}
      />
    </div>
  ) : (
    <div>Loading...</div>
  )
}

export default ImageUpdateForm
