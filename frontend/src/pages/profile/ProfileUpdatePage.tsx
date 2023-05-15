import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfileUpdatePage.module.scss'

import ImageUpdateForm from 'components/user/ImageUpdateForm'
import NicknameUpdateForm from 'components/user/NicknameUpdateForm'
import PwUpdateForm from 'components/user/PwUpdateForm'

function ProfileUpdatePage() {
  const { theme } = useContext(ThemeContext)

  return (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <div className={styles.signout}>회원탈퇴</div>
      <ImageUpdateForm />
      <NicknameUpdateForm />
      <PwUpdateForm />
    </div>
  )
}

export default ProfileUpdatePage
