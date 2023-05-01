import React, { useContext, useState } from 'react'
import styles from './UserProfile.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

import Button from 'components/common/Button'
import UserImage from 'components/common/UserImage'
import IconButton from 'components/common/IconButton'
import UserInfo from 'components/user/UserInfo'

import RankModal from 'components/user/LevelModal'
import Modal from 'components/common/Modal'

import mountain from 'assets/images/mountain.png'
// Todo: 아이콘 버튼으로 바꾸기
import { BiEdit } from 'react-icons/bi'

import { UserRecord } from 'types/user.interface'

type UserProfileProps = {
    imgUrl: string
    nickname: string
    email: string
    userRecord: UserRecord
}

export default function UserProfile({
    imgUrl,
    nickname,
    email,
    userRecord,
}: UserProfileProps) {
    const { theme } = useContext(ThemeContext)
    const [isOpen, setIsOpen] = useState(false)

    return (
        <div className={`${styles['profile-box']}`}>
            {isOpen && (
                <Modal onClick={() => setIsOpen(false)}>
                    <RankModal />
                </Modal>
            )}
            <div className={`content ${theme} ${styles['profile-img']}`}>
                <UserImage size="lg" imgUrl={imgUrl} />
            </div>
            <div className={`content ${theme} ${styles['user-record']}`}>
                <div className={styles['btn-box']}>
                    <Button
                        text={'로그아웃'}
                        size={'sm'}
                        color={'secondary'}
                        onClick={() => console.log('로그아웃')}
                    />
                    <BiEdit />
                </div>
                <div className={styles.username}>
                    {nickname}
                    <IconButton
                        imgSrc={mountain}
                        size={'sm'}
                        onClick={() => setIsOpen(true)}
                    />
                </div>
                <span>{email}</span>
                <UserInfo userRecord={userRecord} />
            </div>
        </div>
    )
}
