import React, { useEffect, useState } from 'react'
import styles from './ClubMemberPage.module.scss'
import { ClubDetailInfo, ClubMemberList } from 'types/club.interface'
import {
  getClubMember,
  updateClubMember,
  deleteClubMember,
  deleteClub,
  getClubInfo,
  useClubSimpleInfoQuery,
} from 'apis/services/clubs'
import Toast from 'components/common/Toast'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'
import useUserQuery from 'hooks/useUserQuery'
import TextButton from 'components/common/TextButton'
import Modal from 'components/common/Modal'
import ConfirmModal from 'components/club/ConfirmModal'
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

  const {
    isLoading: isClubSimpleInfoLoading,
    isError: isClubSimpleInfoError,
    data: clubSimpleInfo,
    isSuccess,
  } = useClubSimpleInfoQuery(clubId || 0)

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
    deleteClub(clubId)
      .then(() => {
        Toast.addMessage('success', `${clubInfo?.clubName}에서 탈퇴하셨습니다`)
        navigate('/club/none')
      })
      .catch(() => {
        Toast.addMessage('error', `클럽 호스트는 탈퇴하실 수 없습니다`)
        setIsOpen(false)
      })
  }

  return clubMemberList.member[0] && clubSimpleInfo ? (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <ConfirmModal
            title="모임을 탈퇴하시겠습니까?"
            content={`주최한 일정이 존재하면, \n삭제 또는 완료해야 탈퇴가능합니다`}
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
            hostId={clubSimpleInfo.hostId}
          />
        )}
        <MemberList
          title="모임 멤버"
          length={clubMemberList.member.length}
          memberList={clubMemberList.member}
          hostId={clubSimpleInfo.hostId}
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
