import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ImageUpdateForm.module.scss'

import Image from 'components/common/Image'

import IconButton from 'components/common/IconButton'
import { TbCameraPlus } from 'react-icons/tb'
// Todo: API 연결
function ImageUpdateForm() {
  const { theme } = useContext(ThemeContext)

  const userInfo = {
    imgUrl:
      'https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80',
    username: '이병호리병',
  }

  const onClickUpdateImg = () => {
    console.log('프로필 수정')
  }
  return (
    <div className={`content ${theme} ${styles.img}`}>
      <Image imgUrl={userInfo.imgUrl} size="lg" />
      <IconButton
        icon={<TbCameraPlus />}
        size="sm"
        color="gray"
        onClick={onClickUpdateImg}
      />
    </div>
  )
}

export default ImageUpdateForm
