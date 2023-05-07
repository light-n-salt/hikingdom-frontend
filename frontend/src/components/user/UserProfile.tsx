import React, { useContext, useState } from 'react'
import styles from './UserProfile.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { useNavigate } from 'react-router-dom'

import Button from 'components/common/Button'
import Image from 'components/common/Image'
import IconButton from 'components/common/IconButton'
import UserInfo from 'components/user/UserInfo'

import LevelModal from 'components/user/LevelModal'
import Modal from 'components/common/Modal'

import { BiEdit } from 'react-icons/bi'
import { UserRecord, User } from 'types/user.interface'
import LEVEL_TO_IMG from 'constants/levels'

import { logout } from 'apis/services/users'

interface UserProfileProps extends UserRecord, User {}

// Todo: level API 업데이트
export default function UserProfile({
  profileUrl,
  nickname,
  email,
  level,
  totalAlt,
  totalDistance,
  totalDuration,
  totalHikingCount,
  totalMountainCount,
}: UserProfileProps) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const [isOpen, setIsOpen] = useState(false)

  const onClickLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <div className={styles.profile}>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <LevelModal />
        </Modal>
      )}
      <div className={`content ${theme} ${styles.img}`}>
        <Image size="lg" imgUrl={profileUrl} />
      </div>
      <div className={`content ${theme} ${styles.record}`}>
        <div className={styles.btns}>
          <Button
            text={'로그아웃'}
            size={'sm'}
            color={'secondary'}
            onClick={onClickLogout}
          />

          <IconButton
            icon={<BiEdit />}
            size="sm"
            color="gray"
            onClick={() => navigate('/profile/update')}
          />
        </div>
        <div className={styles.username}>
          {nickname}
          {level && (
            <IconButton
              imgSrc={LEVEL_TO_IMG[level]}
              size={'sm'}
              onClick={() => setIsOpen(true)}
            />
          )}
        </div>
        <span className={styles.email}>{email}</span>
        <UserInfo
          totalAlt={totalAlt}
          totalDistance={totalDistance}
          totalDuration={totalDuration}
          totalHikingCount={totalHikingCount}
          totalMountainCount={totalMountainCount}
        />
      </div>
    </div>
  )
}
