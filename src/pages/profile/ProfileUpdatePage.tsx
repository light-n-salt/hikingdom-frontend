import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfileUpdatePage.module.scss'

import ImageUpdateForm from 'components/user/ImageUpdateForm'
import NicknameUpdateForm from 'components/user/NicknameUpdateForm'
import PwUpdateForm from 'components/user/PwUpdateForm'
import TextButton from 'components/common/TextButton'
import toast from 'components/common/Toast'
import { useMutation } from '@tanstack/react-query'
import { signout } from 'apis/services/users'
import { useNavigate } from 'react-router-dom'

function ProfileUpdatePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const onClickWithdraw = useMutation(signout, {
    onSuccess: () => {
      toast.addMessage('success', '탈퇴되었습니다')
      navigate('/')
    },
  })

  return (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <div className={styles.signout}>
        <TextButton
          text="회원탈퇴"
          color="red"
          onClick={() => onClickWithdraw.mutate()}
        />
      </div>
      <ImageUpdateForm />
      <NicknameUpdateForm />
      <PwUpdateForm />
    </div>
  )
}

export default ProfileUpdatePage
