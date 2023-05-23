import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfileUpdatePage.module.scss'

import ImageUpdateForm from 'components/user/ImageUpdateForm'
import NicknameUpdateForm from 'components/user/NicknameUpdateForm'
import PwUpdateForm from 'components/user/PwUpdateForm'
import TextButton from 'components/common/TextButton'
import toast from 'components/common/Toast'
import { useMutation } from '@tanstack/react-query'
import { logout, signout } from 'apis/services/users'
import { useNavigate } from 'react-router-dom'
import PageHeader from 'components/common/PageHeader'
import Button from 'components/common/Button'

function ProfileUpdatePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const onClickWithdraw = useMutation(signout, {
    onSuccess: () => {
      toast.addMessage('success', '탈퇴되었습니다')
      navigate('/')
    },
  })

  const onClickLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <div className={`page p-md ${theme} ${styles.profile}`}>
      <PageHeader color="primary" />
      <ImageUpdateForm />
      <NicknameUpdateForm />
      <PwUpdateForm />
      <div className={styles.buttons}>
        <Button
          text={'로그아웃'}
          size={'sm'}
          color={'secondary'}
          onClick={onClickLogout}
        />
        <TextButton
          text="회원탈퇴"
          color="red"
          onClick={() => onClickWithdraw.mutate()}
        />
      </div>
    </div>
  )
}

export default ProfileUpdatePage
