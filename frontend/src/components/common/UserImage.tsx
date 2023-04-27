import React, { useContext } from 'react'
import styles from './UserImage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

type UserImageProps = {
    size: 'sm' | 'lg'
    imgUrl: string
}

export default function UserImage({ size, imgUrl }: UserImageProps) {
    const { theme } = useContext(ThemeContext)
    return <img className={`${styles[theme]} ${styles[size]}`} src={imgUrl} />
}
