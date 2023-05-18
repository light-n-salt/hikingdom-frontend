import React, { useMemo } from 'react'
import styles from './ClubHeader.module.scss'
import { useQuery } from '@tanstack/react-query'
import { NavLink, useNavigate } from 'react-router-dom'
import { getClubSimpleInfo } from 'apis/services/clubs'
import Chatting from 'assets/images/airplane.png'
import IconButton from 'components/common/IconButton'
import useUserQuery from 'hooks/useUserQuery'
import { untilMidnight } from 'utils/untilMidnight'
import { ClubSimpleInfo } from 'types/club.interface'

function ClubHeader() {
  const navigate = useNavigate()

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const queryTime = useMemo(() => {
    return untilMidnight()
  }, [])

  const { data: clubInfo } = useQuery<ClubSimpleInfo>(
    ['user', 'clubInfo'],
    () => getClubSimpleInfo(clubId || 0),
    {
      cacheTime: queryTime,
      staleTime: queryTime,
      enabled: !!clubId,
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
            onClick={() => navigate(`/club/chat`)}
          />
        </div>
      </div>
      <nav className={styles.nav}>
        <NavLink
          to="main"
          className={({ isActive }) =>
            isActive ? styles.active : styles.inactive
          }
        >
          모임
        </NavLink>
        <NavLink
          to="meetup"
          className={({ isActive }) =>
            isActive ? styles.active : styles.inactive
          }
        >
          일정
        </NavLink>
        <NavLink
          to="member"
          className={({ isActive }) =>
            isActive ? styles.active : styles.inactive
          }
        >
          멤버
        </NavLink>
        <NavLink
          to="album"
          className={({ isActive }) =>
            isActive ? styles.active : styles.inactive
          }
        >
          앨범
        </NavLink>
      </nav>
    </div>
  ) : null
}

export default ClubHeader
