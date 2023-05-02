import React, { useContext, useState } from 'react'
import styles from './UserProfile.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { useNavigate } from 'react-router-dom'

import Button from 'components/common/Button'
import UserImage from 'components/common/UserImage'
import IconButton from 'components/common/IconButton'
import UserInfo from 'components/user/UserInfo'

import RankModal from 'components/user/LevelModal'
import Modal from 'components/common/Modal'

import mountain from 'assets/images/mountain.png'

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
    const navigate = useNavigate()
    const [isOpen, setIsOpen] = useState(false)

    return (
        <div className={styles.profile}>
            {isOpen && (
                <Modal onClick={() => setIsOpen(false)}>
                    <RankModal />
                </Modal>
            )}
            <div className={`content ${theme} ${styles.img}`}>
                <UserImage size="lg" imgUrl={imgUrl} />
            </div>
            <div className={`content ${theme} ${styles.record}`}>
                <div className={styles.btns}>
                    <Button
                        text={'로그아웃'}
                        size={'sm'}
                        color={'secondary'}
                        onClick={() => console.log('로그아웃')}
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
                    <IconButton
                        imgSrc={mountain}
                        size={'sm'}
                        onClick={() => setIsOpen(true)}
                    />
                </div>
                <span className={styles.email}>{email}</span>
                <UserInfo userRecord={userRecord} />
            </div>
        </div>
    )
}
