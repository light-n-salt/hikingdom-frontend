import React, { useEffect, useState } from 'react'
import styles from './WaitingListModal.module.scss'
import { getClubRequest } from 'apis/services/clubs'
import RankList from 'components/common/RankList'

import { ClubInfo } from 'types/club.interface'
import { deleteClubRequest } from 'apis/services/clubs'
import Toast from 'components/common/Toast'

function WaitingListModal() {
  const [clubInfoArray, setClubInfoArray] = useState<ClubInfo[]>([])

  useEffect(() => {
    getClubRequest().then((res) => setClubInfoArray(res.data.result))
  }, [])

  // 소모임 신청 취소 함수
  function onClickDeleteClub(clubId: number, clubName: string) {
    deleteClubRequest(clubId)
      .then(() => {
        Toast.addMessage('success', `${clubName}의 가입 신청을 취소했습니다.`)
        getClubRequest().then((res) => setClubInfoArray(res.data.result))
      })
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  return (
    <div className={styles.list}>
      <h2>가입 대기 중 모임</h2>
      {clubInfoArray[0] ? (
        <RankList
          clubInfoArray={clubInfoArray}
          size="lg"
          onClickDeleteClub={onClickDeleteClub}
        />
      ) : (
        <span>가입 신청한 모임이 없습니다.</span>
      )}
    </div>
  )
}

export default WaitingListModal
