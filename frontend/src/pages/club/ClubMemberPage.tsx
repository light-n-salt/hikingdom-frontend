import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router'
import styles from './ClubMemberPage.module.scss'
import { ClubMemberList } from 'types/club.interface'
import {
  getClubMember,
  updateClubMember,
  deleteClubMember,
} from 'apis/services/clubs'
import Toast from 'components/common/Toast'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'

function ClubMemberPage() {
  const [clubMemberList, setClubMemberList] = useState<ClubMemberList>({
    member: [],
  })

  const clubId = useParams<string>().clubId

  useEffect(() => {
    getClubMemberList(Number(clubId))
  }, [])

  // 멤버 조회
  function getClubMemberList(clubId: number) {
    getClubMember(Number(clubId)).then((res) =>
      setClubMemberList(res.data.result)
    )
  }

  // 수락 함수
  function onClickJoin(memberId: number) {
    updateClubMember(Number(clubId), memberId)
      .then(() => getClubMemberList(Number(clubId)))
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  // 거절 함수
  function onClickDelete(memberId: number) {
    deleteClubMember(Number(clubId), memberId)
      .then(() => getClubMemberList(Number(clubId)))
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  return clubMemberList.member[0] ? (
    <div className={styles.page}>
      {clubMemberList.request && (
        <MemberList
          title="가입대기"
          length={clubMemberList.request.length}
          memberList={clubMemberList.request}
          onClickJoin={onClickJoin}
          onClickDelete={onClickDelete}
        />
      )}
      <MemberList
        title="모임 멤버"
        length={clubMemberList.member.length}
        memberList={clubMemberList.member}
      />
    </div>
  ) : (
    <Loading />
  )
}

export default ClubMemberPage
