import React from 'react'
import styles from './Image.module.scss'

type ImageProps = {
  size: 'sm' | 'md' | 'lg'
  imgUrl: string
  isSquare?: boolean
}

function Image({ size, imgUrl, isSquare }: ImageProps) {
  const imgStyle = isSquare ? styles.square : ''

  return (
    <img
      className={`${styles['user-img']} ${styles[size]} ${imgStyle}`}
      src={imgUrl}
    />
  )
}

export default Image
