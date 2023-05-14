import React, { useEffect, useState } from 'react'
import styles from './WaitingListModal.module.scss'

import { ClubInfo } from 'types/club.interface'
import { getClubRequest } from 'apis/services/clubs'
import { deleteClubRequest } from 'apis/services/clubs'
import { useQuery, useQueryClient } from '@tanstack/react-query'
import Toast from 'components/common/Toast'
import RankList from 'components/common/RankList'

function WaitingListModal() {
  const queryClient = useQueryClient()

  const { data: clubInfoArray = [] } = useQuery<ClubInfo[]>(['requestClubList'], getClubRequest)

  // 소모임 신청 취소 함수
  function onClickDeleteClub(clubId: number, clubName: string) {
    deleteClubRequest(clubId)
      .then(() => {
        queryClient.invalidateQueries(['requestClubList'])
        Toast.addMessage('success', `${clubName}의 가입 신청을 취소했습니다.`)
      })
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  return (
    <div className={styles.list}>
      <h2>가입 대기 중 모임</h2>
      {clubInfoArray?.length > 0 ? (
        <RankList
          clubInfoArray={clubInfoArray}
          size="lg"
          onClickDeleteClub={onClickDeleteClub}
        />
      ) : (
        <span className={styles.text}>가입 신청한 모임이 없습니다.</span>
      )}
  </div>
  )
}

export default WaitingListModal
