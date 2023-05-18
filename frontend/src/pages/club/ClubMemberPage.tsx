import React, { useEffect, useState } from 'react'
import styles from './ClubMemberPage.module.scss'
import { ClubDetailInfo, ClubMemberList } from 'types/club.interface'
import {
  getClubMember,
  updateClubMember,
  deleteClubMember,
  deleteClub,
  getClubInfo,
} from 'apis/services/clubs'
import Toast from 'components/common/Toast'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'
import useUserQuery from 'hooks/useUserQuery'
import TextButton from 'components/common/TextButton'
import Modal from 'components/common/Modal'
import DeleteModal from 'components/club/DeleteModal'
import { useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'

function ClubMemberPage() {
  const navigate = useNavigate()

  const [isOpen, setIsOpen] = useState(false)
  const [clubMemberList, setClubMemberList] = useState<ClubMemberList>({
    member: [],
  })

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const { data: clubInfo } = useQuery<ClubDetailInfo>(
    ['ClubDetailInfo', clubId],
    () => getClubInfo(clubId || 0),
    {
      enabled: !!clubId,
    }
  )

  useEffect(() => {
    if (!clubId) return
    getClubMemberList(clubId)
  }, [clubId])

  // 멤버 조회
  function getClubMemberList(clubId: number) {
    getClubMember(clubId).then((res) => setClubMemberList(res.data.result))
  }

  // 수락 함수
  function onClickJoin(memberId: number) {
    if (!clubId) return
    updateClubMember(clubId, memberId)
      .then(() => getClubMemberList(Number(clubId)))
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  // 거절 함수
  function onClickDelete(memberId: number) {
    if (!clubId) return
    deleteClubMember(clubId, memberId)
      .then(() => getClubMemberList(Number(clubId)))
      .catch((err) => Toast.addMessage('error', `${err.data.message}`))
  }

  function onClickDeleteClub() {
    if (!clubId) return
    deleteClub(clubId).then(() => {
      Toast.addMessage('success', `${clubInfo?.clubName}에서 탈퇴하셨습니다`)
      navigate('/club/none')
    })
  }

  return clubMemberList.member[0] ? (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <DeleteModal
            title="모임을 탈퇴하시겠습니까?"
            content1={`주최한 일정이 존재하면, \n삭제 또는 완료해야 탈퇴가능합니다`}
            content2="탈퇴한 모임은 다시 가입을 신청해야 합니다"
            buttonText="모임 탈퇴"
            onClickDelete={onClickDeleteClub}
            onClickCloseModal={() => setIsOpen(false)}
          />
        </Modal>
      )}
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
        <div className={styles.button}>
          <TextButton
            text="모임탈퇴"
            size="sm"
            color="gray"
            onClick={() => setIsOpen(true)}
          />
        </div>
      </div>
    </>
  ) : (
    <Loading />
  )
}

export default ClubMemberPage
