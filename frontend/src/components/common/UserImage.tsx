import React, { useContext } from 'react'
import styles from './UserImage.module.scss'

type UserImageProps = {
    size: 'sm' | 'lg'
    imgUrl: string
}

function UserImage({ size, imgUrl }: UserImageProps) {
    return <img className={`${styles[size]}`} src={imgUrl} />
}

export default UserImage
