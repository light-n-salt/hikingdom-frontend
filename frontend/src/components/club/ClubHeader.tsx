import React, { useEffect } from 'react'
import styles from './ClubHeader.module.scss'
import { useLocation, useNavigate } from 'react-router-dom'

import { ClubSimpleInfo } from 'types/club.interface'
import { clubSimpleInfo } from 'apis/services/clubs'
import Chatting from 'assets/images/airplane.png'

import IconButton from 'components/common/IconButton'
import TextButton from 'components/common/TextButton'

function ClubHeader() {
  const navigate = useNavigate()

  const clubInfo: ClubSimpleInfo = {
    hostId: 1,
    clubId: 1,
    clubName: '산타마리아',
  }

  const location = useLocation()
  const type = location.pathname.split('/')[2]

  useEffect(() => {
    // clubSimpleInfo(clubId)
    //   .then((res) => {})
    //   .catch(() => {})
  }, [])

  return (
    <>
      <div className={styles.header}>
        <span className={styles.title}>{clubInfo.clubName}</span>
        <div className={styles.chat}>
          <IconButton
            imgSrc={Chatting}
            size="md"
            onClick={() => navigate('/club/chat')}
          />
        </div>
      </div>
      <div className={styles.nav}>
        <span
          className={`${styles.button} ${
            type === 'main' ? styles.active : styles.disabled
          }`}
          onClick={() => navigate(`main/${clubInfo.clubId}`)}
        >
          모임
        </span>
        <span
          className={`${styles.button} ${
            type === 'meetup' ? styles.active : styles.disabled
          }`}
          onClick={() => navigate('meetup')}
        >
          일정
        </span>
        <span
          className={`${styles.button} ${
            type === 'member' ? styles.active : styles.disabled
          }`}
          onClick={() => navigate('member')}
        >
          멤버
        </span>
        <span
          className={`${styles.button} ${
            type === 'album' ? styles.active : styles.disabled
          }`}
          onClick={() => navigate('album')}
        >
          앨범
        </span>
      </div>
    </>
  )
}

export default ClubHeader
