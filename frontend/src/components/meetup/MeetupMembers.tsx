import React, { useState } from 'react'
import styles from './MeetupMembers.module.scss'

import Image from 'components/common/Image'
import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import MemberModal from './MemberModal'
import { MeetupMemberInfo } from 'types/meetup.interface'
import { ClubMember } from 'types/club.interface'
import toast from 'components/common/Toast'
import { useQuery, useQueryClient, useMutation } from '@tanstack/react-query'
import { useParams } from 'react-router-dom'
import {
  getMeetupMembers,
  getMembersDetail,
  updateJoin,
  deleteJoin,
} from 'apis/services/meetup'
import useUserQuery from 'hooks/useUserQuery'

function MeetupMembers() {
  const queryClient = useQueryClient()
  const [isOpen, setIsOpen] = useState(false)
  const [memberDetail, setMemberDetail] = useState<ClubMember[]>()
  const { meetupId } = useParams() as {
    meetupId: string
  }

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  // 일정 멤버 조회
  const { data: members } = useQuery<MeetupMemberInfo>(['meetupMembers'], () =>
    getMeetupMembers(clubId || 0, Number(meetupId))
  )

  // 모달 ON OFF
  const onClickDetail = () => {
    if (!clubId) return
    setIsOpen(true)
    getMembersDetail(clubId, parseInt(meetupId)).then((res) =>
      setMemberDetail(res)
    )
  }

  // 일정 참여
  const { mutate: onClickJoin } = useMutation(
    () => updateJoin(Number(clubId), Number(meetupId)),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['meetupMembers'])
        queryClient.invalidateQueries(['meetup'])
        toast.addMessage('success', '일정에 참여했습니다')
      },
      onError: (err: any) => {
        toast.addMessage('error', err.data.message)
      },
    }
  )

  // 일정 참여 취소
  const { mutate: onClickWithdraw } = useMutation(
    () => deleteJoin(Number(clubId), Number(meetupId)),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['meetupMembers'])
        queryClient.invalidateQueries(['meetup'])
        toast.addMessage('success', '일정을 취소했습니다')
      },
      onError: (err: any) => {
        toast.addMessage('error', err.data.message)
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
          <span className={styles.title}>
            참여 멤버 ({members?.totalMember}명)
          </span>
          {members?.isJoin ? (
            <Button
              text="참여취소"
              color="secondary"
              size="xs"
              onClick={() => onClickWithdraw()}
            />
          ) : (
            <Button
              text="참여"
              color="primary"
              size="xs"
              onClick={() => onClickJoin()}
            />
          )}
        </div>
        <div className={styles.members}>
          {members?.memberInfo.slice(0, 6).map((member) => (
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
