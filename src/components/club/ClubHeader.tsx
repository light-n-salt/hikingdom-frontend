import React, { useMemo } from 'react'
import styles from './ClubHeader.module.scss'
import { useLocation, useNavigate, useParams } from 'react-router-dom'

import { getClubSimpleInfo } from 'apis/services/clubs'
import { ClubSimpleInfo } from 'types/club.interface'
import { useQuery } from '@tanstack/react-query'
import { untilMidnight } from 'utils/untilMidnight'
import Chatting from 'assets/images/airplane.png'
import IconButton from 'components/common/IconButton'

function ClubHeader() {
  const navigate = useNavigate()

  const location = useLocation()
  const type = location.pathname.split('/')[3]

  const clubId = useParams<string>().clubId

  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const { data: clubInfo } = useQuery<ClubSimpleInfo>(
    ['clubInfo'],
    () => getClubSimpleInfo(Number(clubId)),
    {
      cacheTime: queryTime,
      staleTime: queryTime,
    }
  )

  return clubInfo ? (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.title}>{clubInfo.clubName}</h1>
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
            type === 'main' ? styles.active : styles.inactive
          }`}
          onClick={() => navigate(`main`)}
        >
          모임
        </span>
        <span
          className={`${styles.button} ${
            type === 'meetup' ? styles.active : styles.inactive
          }`}
          onClick={() => navigate('meetup')}
        >
          일정
        </span>
        <span
          className={`${styles.button} ${
            type === 'member' ? styles.active : styles.inactive
          }`}
          onClick={() => navigate('member')}
        >
          멤버
        </span>
        <span
          className={`${styles.button} ${
            type === 'album' ? styles.active : styles.inactive
          }`}
          onClick={() => navigate('album')}
        >
          앨범
        </span>
      </div>
    </div>
  ) : null
}

export default ClubHeader
