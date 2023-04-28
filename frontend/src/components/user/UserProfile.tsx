import React, { useContext } from 'react'
import styles from './UserProfile.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

import Button from 'components/common/Button'
// import IconButton from 'components/common/IconButton'
import UserImage from 'components/common/UserImage'
import IconText from 'components/common/IconText'
import UserInfo from 'components/user/UserInfo'

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

    return (
        <div className={`${styles['profile-box']}`}>
            <div className={`${styles['profile-img']} ${styles[theme]}`}>
                <UserImage size="lg" imgUrl={imgUrl} />
            </div>
            <div className={`box ${theme} ${styles['user-record']}`}>
                <div className={styles['btn-box']}>
                    <Button
                        text={'로그아웃'}
                        size={'sm'}
                        color={'secondary'}
                        onClick={() => console.log('로그아웃')}
                    />
                    <BiEdit />
                </div>
                <IconText
                    imgSrc={mountain}
                    text={nickname}
                    size={'md'}
                    isBold={true}
                    isRight={true}
                />
                <span>{email}</span>
                <UserInfo userRecord={userRecord} />
            </div>
        </div>
    )
}
