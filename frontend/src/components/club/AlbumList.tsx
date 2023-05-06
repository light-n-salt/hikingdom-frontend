import React from 'react'
import styles from './AlbumList.module.scss'
import { Album } from 'types/club.interface'

type AlbumListProps = {
  photoList: Album[]
}

function AlbumList({ photoList }: AlbumListProps) {
  return (
    <div className={styles.container}>
      {photoList.map((photo) => (
        <img key={photo.photoId} src={photo.imgUrl} className={styles.img} />
      ))}
    </div>
  )
}

export default AlbumList
