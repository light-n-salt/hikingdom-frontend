import React, { useContext, useState } from 'react'
import styles from './UserProfile.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { useNavigate, useParams } from 'react-router-dom'

import Button from 'components/common/Button'
import Image from 'components/common/Image'
import IconButton from 'components/common/IconButton'
import UserInfo from 'components/user/UserInfo'

import LevelModal from 'components/user/LevelModal'
import Modal from 'components/common/Modal'
import toast from 'components/common/Toast'

import { BiEdit } from 'react-icons/bi'
import { HiLightBulb } from 'react-icons/hi'
import { FiChevronLeft } from 'react-icons/fi'
import { FaSun, FaMoon } from 'react-icons/fa'
import { UserRecord, User } from 'types/user.interface'
import LEVEL_TO_IMG from 'constants/levels'
import bell from 'assets/images/bell.png'

import { logout, report } from 'apis/services/users'
import useUserQuery from 'hooks/useUserQuery'
import PageHeader from 'components/common/PageHeader'

interface UserProfileProps extends UserRecord, User {}

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
  const { theme, toggleTheme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const [isOpen, setIsOpen] = useState(false)
  const { nickname: userNickname } = useParams() as { nickname: string }
  const { data: userInfo } = useUserQuery()

  const onClickReport = () => {
    report('MEMBER', userNickname).then(() => {
      toast.addMessage('success', '신고가 완료됐습니다')
    })
  }

  // 내 자신이 아니면
  // 로그아웃, 수정, 알람버튼 숨기기
  // 신고하기 보이기
  const stranger = userNickname !== userInfo?.nickname ? styles.stranger : ''

  const themeIcon = theme === 'light' ? <FaSun /> : <FaMoon />

  return (
    <div className={styles.profile}>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <LevelModal />
        </Modal>
      )}
      <div className={`${styles['alarm-siren']}`}>
        <PageHeader color="dark" />
        <div className={`${stranger} ${styles.siren}`} onClick={onClickReport}>
          <HiLightBulb /> 신고하기
        </div>
        <div className={`${stranger} ${styles.alarm}`}>
          <IconButton
            size="sm"
            imgSrc={bell}
            onClick={() => navigate('/alarm')}
          />
        </div>
      </div>
      <div className={`content ${theme} ${styles.img}`}>
        <Image size="lg" imgUrl={profileUrl} />
      </div>
      <div className={`content ${theme} ${styles.record}`}>
        <div className={`${stranger} ${styles.btns}`}>
          {/* <IconButton
            icon={themeIcon}
            size="sm"
            color="gray"
            onClick={toggleTheme}
          /> */}
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
