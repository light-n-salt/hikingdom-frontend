import React from 'react'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'
import { useMembersDetailQuery } from 'apis/services/meetup'

type MemberModalProps = {
  clubId: number
  meetupId: number
}

function MemberModal({ clubId, meetupId }: MemberModalProps) {
  const {
    isLoading,
    isError,
    data: memberList,
  } = useMembersDetailQuery(clubId, Number(meetupId))

  return (
    <div>
      {isLoading || isError ? (
        <Loading />
      ) : (
        <MemberList
          title="참여 멤버"
          length={memberList.length}
          memberList={memberList}
        />
      )}
    </div>
  )
}

export default MemberModal
