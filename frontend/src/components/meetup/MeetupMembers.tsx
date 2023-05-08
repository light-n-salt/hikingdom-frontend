import React, { useState, useEffect } from 'react'
import styles from './MeetupMembers.module.scss'

import Image from 'components/common/Image'
import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import MemberModal from './MemberModal'
import { MeetupMember } from 'types/meetup.interface'
import { ClubMember } from 'types/club.interface'
import toast from 'components/common/Toast'

import { useParams } from 'react-router-dom'
import { useQuery, useMutation } from '@tanstack/react-query'
import {
  getMeeupMembers,
  updateMeetup,
  deleteMeetup,
} from 'apis/services/meetup'

type MeetupMembersProps = {
  memberInfo: MeetupMember[]
  join: boolean
}

function MeetupMembers({ memberInfo, join }: MeetupMembersProps) {
  const [members, setMembers] = useState(memberInfo)
  const [isOpen, setIsOpen] = useState(false)
  const { clubId, meetupId } = useParams() as {
    clubId: string
    meetupId: string
  }

  const { data: memberDetail } = useQuery<ClubMember[]>(
    ['memberList'],
    () => getMeeupMembers(parseInt(clubId), parseInt(meetupId)),
    { enabled: !!isOpen } // 모달을 눌렀을 때 실행
  )

  // 모달
  const onClickOpen = () => {
    setIsOpen(true)
  }

  // 일정 참여
  const onClickJoin = useMutation(
    () => updateMeetup(parseInt(clubId), parseInt(meetupId)),
    {
      onSuccess: () => {
        toast.addMessage('success', '일정에 참여했습니다.')
      },
      onError: () => {
        toast.addMessage('error', '참여할 수 없습니다.')
      },
    }
  )

  // 일정 참여 취소
  const onClickWithdraw = useMutation(
    () => deleteMeetup(parseInt(clubId), parseInt(meetupId)),
    {
      onSuccess: () => {
        toast.addMessage('success', '일정 참여를 취소했습니다.')
      },
    }
  )

  return (
    <>
      {isOpen && memberDetail && (
        <Modal onClick={() => setIsOpen(false)}>
          <MemberModal memberList={memberDetail} />
        </Modal>
      )}
      <div className={styles.meetup}>
        <div className={styles.titles}>
          <span className={styles.title}>참여 멤버 (N명)</span>
          {join ? (
            <Button
              text="참여취소"
              color="secondary"
              size="xs"
              onClick={() => onClickWithdraw.mutate()}
            />
          ) : (
            <Button
              text="참여"
              color="primary"
              size="xs"
              onClick={() => onClickJoin.mutate()}
            />
          )}
        </div>
        <div className={styles.members}>
          {members.map((member) => (
            <Image
              key={member.memberId}
              imgUrl={member.profileUrl}
              size="sm"
              isSquare={true}
            />
          ))}
          <div className={styles.more} onClick={onClickOpen}>
            +
          </div>
        </div>
      </div>
    </>
  )
}

export default MeetupMembers
