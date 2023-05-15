import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubAlbumPage.module.scss'
import AlbumList from 'components/club/AlbumList'

import { Album } from 'types/club.interface'

// Todo: API 연결

function ClubAlbumPage() {
  const { theme } = useContext(ThemeContext)

  const photoList: Album[] = [
    {
      photoId: 0,
      memberId: 0,
      imgUrl:
        'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
      createdAt: 'YYYY-MM-DD HH:mm',
    },
    {
      photoId: 1,
      memberId: 1,
      imgUrl:
        'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
      createdAt: 'YYYY-MM-DD HH:mm',
    },
    {
      photoId: 2,
      memberId: 2,
      imgUrl:
        'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
      createdAt: 'YYYY-MM-DD HH:mm',
    },
    {
      photoId: 3,
      memberId: 3,
      imgUrl:
        'https://i.namu.wiki/i/IMJTGYaO0v6OYa5iro8bLfHuxdiXflvRQ4BdsMVOEvhFP2VBF74QAdMc4PFs-dJcYu-b9aRFeEnajUO1nDQeDg.webp',
      createdAt: 'YYYY-MM-DD HH:mm',
    },
  ]
  const cnt = 4

  return (
    <div className={`page p-sm ${theme}`}>
      <div className={styles.cnt}>[ {cnt} ]</div>
      <AlbumList photoList={photoList} />
    </div>
  )
}
export default ClubAlbumPage
