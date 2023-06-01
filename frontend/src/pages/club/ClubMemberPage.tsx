import React, { useEffect, useState } from 'react'
import styles from './ClubMemberPage.module.scss'
import {
  useDeleteClub,
  useClubSimpleInfoQuery,
  useClubMemberQuery,
} from 'apis/services/clubs'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'
import useUserQuery from 'hooks/useUserQuery'
import TextButton from 'components/common/TextButton'
import Modal from 'components/common/Modal'
import ConfirmModal from 'components/club/ConfirmModal'
import { useNavigate } from 'react-router-dom'

function ClubMemberPage() {
  const [isOpen, setIsOpen] = useState(false)

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const {
    isLoading: isClubSimpleInfoLoading,
    isError: isClubSimpleInfoError,
    data: clubSimpleInfo,
    isSuccess: isClubSimpleInfoSuccess,
  } = useClubSimpleInfoQuery(clubId || 0)

  const {
    isLoading,
    isError,
    data: clubMemberList,
    isSuccess,
  } = useClubMemberQuery(clubId || 0)

  // 모임 탈퇴 mutation 호출
  const { mutateAsync: deleteClub } = useDeleteClub(clubId || 0)

  // 모임 탈퇴 함수
  function onClickDeleteClub() {
    if (!clubId) return
    deleteClub().catch(() => {
      setIsOpen(false)
    })
  }

  return isSuccess && clubSimpleInfo ? (
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
            hostId={clubSimpleInfo.hostId}
            isRequest={true}
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
