import React, { useEffect } from 'react'
import styles from './ClubHeader.module.scss'
import { useNavigate } from 'react-router-dom'

import { ClubSimpleInfo } from 'types/club.interface'
import { clubSimpleInfo } from 'apis/services/clubs'
import Chatting from 'assets/images/airplane.png'

import IconButton from 'components/common/IconButton'
import TextButton from 'components/common/TextButton'

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
    <>
      <div className={styles.header}>
        <span className={styles.title}>{ClubInfo.clubName}</span>

        <IconButton
          imgSrc={Chatting}
          size="md"
          onClick={() => console.log('채팅')}
        />
      </div>
      <div className={styles.nav}>
        <TextButton text="모임" size="lg" color="gray" />
        <TextButton text="일정" size="lg" color="gray" />
        <TextButton text="멤버" size="lg" color="gray" />
        <TextButton text="앨범" size="lg" color="gray" />
      </div>
    </>
  )
}

export default ClubHeader
