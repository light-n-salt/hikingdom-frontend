import React from 'react'
import styles from './MeetupAlbum.module.scss'

import Image from 'components/common/Image'
import Button from 'components/common/Button'
import { Album } from 'types/club.interface'

type MeetupAlbumProps = {
  photoInfo: Album[]
}

function MeetupAlbum({ photoInfo }: MeetupAlbumProps) {
  return (
    <div className={styles.album}>
      <div className={styles.titles}>
        <span className={styles.title}>추억</span>
        <Button text="추가" color="primary" size="xs" />
      </div>
      <div className={styles.photos}>
        {photoInfo.map((photo) => (
          <Image
            key={photo.photoId}
            imgUrl={photo.imgUrl}
            size="lg"
            isSquare={true}
          />
        ))}
      </div>
    </div>
  )
}

export default MeetupAlbum
