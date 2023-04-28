import React from 'react'
import styles from './UserImage.module.scss'

type UserImageProps = {
    size: 'sm' | 'lg'
    imgUrl: string
}

function UserImage({ size, imgUrl }: UserImageProps) {
    return (
        <img className={`${styles['user-img']} ${styles[size]}`} src={imgUrl} />
    )
}

export default UserImage
