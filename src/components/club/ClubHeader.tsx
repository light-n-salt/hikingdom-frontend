import React, { useEffect } from 'react'
import styles from './ClubHeader.module.scss'
import { useNavigate } from 'react-router-dom'

import { ClubSimpleInfo } from 'types/club.interface'
import { clubSimpleInfo } from 'apis/services/clubs'
import Chatting from 'assets/images/airplane.png'

function ClubHeader() {
  const navigate = useNavigate()

  const ClubInfo: ClubSimpleInfo = {
    hostId: 1,
    clubId: 1,
    clubName: '산타마리아',
  }

  useEffect(() => {
    // clubSimpleInfo(clubId)
    //   .then((res) => {})
    //   .catch(() => {})
  })

  return (
    <div className={styles.header}>
      <span className={styles.title}>{ClubInfo.clubName}</span>
      <img src={Chatting} className={styles.image} />
    </div>
  )
}

export default ClubHeader
