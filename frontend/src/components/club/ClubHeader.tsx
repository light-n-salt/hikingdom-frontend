import React, { useEffect } from 'react'
import styles from './ClubHeader.module.scss'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

import { getClubSimpleInfo } from 'apis/services/clubs'
// import { ClubSimpleInfo } from 'types/club.interface'
import { clubInfoState } from 'recoil/atoms'
import { useRecoilState } from 'recoil'
import Chatting from 'assets/images/airplane.png'
import IconButton from 'components/common/IconButton'
import TextButton from 'components/common/TextButton'

function ClubHeader() {
  const navigate = useNavigate()

  const [clubInfo, setClubInfo] = useRecoilState(clubInfoState)

  const location = useLocation()
  const type = location.pathname.split('/')[3]

  const clubId = useParams<string>().clubId

  useEffect(() => {
    getClubSimpleInfo(Number(clubId))
      .then((res) => {
        setClubInfo(res.data.result)
      })
      .catch(() => {})
  }, [])

  return clubInfo.clubName ? (
    <>
      <div className={styles.header}>
        <span className={styles.title}>{clubInfo.clubName}</span>
        <div className={styles.chat}>
          <IconButton
            imgSrc={Chatting}
            size="sm"
            onClick={() => navigate(`/club/${clubInfo.clubId}/chat`)}
          />
        </div>
      </div>
      <div className={styles.nav}>
        <span
          className={`${styles.button} ${
            type === 'main' ? styles.active : styles.disabled
          }`}
          onClick={() => navigate(`main`)}
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
  ) : null
}

export default ClubHeader
