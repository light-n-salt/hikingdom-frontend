import React, { useState, useEffect } from 'react'
import styles from './MeetupMembers.module.scss'

import Image from 'components/common/Image'
import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import MemberModal from './MemberModal'
import { MeetupMemberInfo } from 'types/meetup.interface'
import { ClubMember } from 'types/club.interface'
import toast from 'components/common/Toast'

import { useParams } from 'react-router-dom'
import {
  getMeetupMembers,
  getMembersDetail,
  updateJoin,
  deleteJoin,
} from 'apis/services/meetup'

function MeetupMembers() {
  const [isOpen, setIsOpen] = useState(false)
  const [members, setMembers] = useState<MeetupMemberInfo>()
  const [memberDetail, setMemberDetail] = useState<ClubMember[]>()
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }

  const updateMembers = () => {
    getMeetupMembers(parseInt(clubId), parseInt(meetupId)).then((res) =>
      setMembers(res)
    )
  }

  // 모달 ON OFF
  const onClickDetail = () => {
    setIsOpen(true)
    getMembersDetail(parseInt(clubId), parseInt(meetupId)).then((res) =>
      setMemberDetail(res)
    )
  }

  // 일정 참여
  const onClickJoin = () => {
    updateJoin(parseInt(clubId), parseInt(meetupId)).then(() => {
      updateMembers()
      toast.addMessage('success', '일정에 참여했습니다')
    })
  }

  // 일정 참여 취소
  const onClickWithdraw = () => {
    deleteJoin(parseInt(clubId), parseInt(meetupId)).then(() => {
      updateMembers()
      toast.addMessage('success', '일정을 취소했습니다')
    })
  }

  useEffect(() => {
    updateMembers()
  }, [])

  return (
    <>
      {isOpen && memberDetail && (
        <Modal onClick={() => setIsOpen(false)}>
          <MemberModal memberList={memberDetail} />
        </Modal>
      )}
      <div className={styles.meetup}>
        <div className={styles.titles}>
          <span className={styles.title}>
            참여 멤버 ({members?.totalMember}명)
          </span>
          {members?.isJoin ? (
            <Button
              text="참여취소"
              color="secondary"
              size="xs"
              onClick={onClickWithdraw}
            />
          ) : (
            <Button
              text="참여"
              color="primary"
              size="xs"
              onClick={onClickJoin}
            />
          )}
        </div>
        <div className={styles.members}>
          {members?.memberInfo.map((member) => (
            <Image
              key={member.memberId}
              imgUrl={member.profileUrl}
              size="sm"
              isSquare={true}
            />
          ))}
          <div className={styles.more} onClick={onClickDetail}>
            +
          </div>
        </div>
      </div>
    </>
  )
}

export default MeetupMembers
