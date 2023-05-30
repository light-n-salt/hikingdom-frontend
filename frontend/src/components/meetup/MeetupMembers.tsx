import React, { useState, useEffect } from 'react'
import styles from './MeetupMembers.module.scss'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import Modal from 'components/common/Modal'
import MemberModal from './MemberModal'
import { ClubMember } from 'types/club.interface'
import { useParams } from 'react-router-dom'
import {
  useMeetupMemberQuery,
  useJoinMeetup,
  useUnJoinMeetup,
} from 'apis/services/meetup'
import useUserQuery from 'hooks/useUserQuery'

function MeetupMembers() {
  const [isOpen, setIsOpen] = useState(false)
  const [memberDetail, setMemberDetail] = useState<ClubMember[]>()
  const { meetupId } = useParams() as {
    meetupId: string
  }

  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  // 일정 멤버 조회
  const {
    isLoading: isMeetupMembersLoading,
    isError: isMeetupMembersError,
    data: meetupMembers,
  } = useMeetupMemberQuery(clubId || 0, Number(meetupId))

  // 일정 참여
  const {
    isLoading: isJoinLoading,
    isError: isJoinError,
    joinMeetup,
  } = useJoinMeetup(clubId || 0, Number(meetupId))

  // 일정 참여 취소
  const {
    isLoading: isUnjoinLoading,
    isError: isUnjoinError,
    unJoinMeetup,
  } = useUnJoinMeetup(clubId || 0, Number(meetupId))

  return (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <MemberModal clubId={clubId} meetupId={meetupId} />
        </Modal>
      )}
      <div className={styles.meetup}>
        <div className={styles.titles}>
          <span className={styles.title}>
            참여 멤버 ({meetupMembers?.totalMember}명)
          </span>
          {meetupMembers?.isJoin ? (
            <Button
              text="참여취소"
              color="secondary"
              size="xs"
              onClick={() => unJoinMeetup()}
            />
          ) : (
            <Button
              text="참여"
              color="primary"
              size="xs"
              onClick={() => joinMeetup()}
            />
          )}
        </div>
        <div className={styles.members}>
          {meetupMembers?.memberInfo.slice(0, 6).map((member) => (
            <Image
              key={member.memberId}
              imgUrl={member.profileUrl}
              size="sm"
              isSquare={true}
            />
          ))}
          <div className={styles.more} onClick={() => setIsOpen(true)}>
            +
          </div>
        </div>
      </div>
    </>
  )
}

export default MeetupMembers
