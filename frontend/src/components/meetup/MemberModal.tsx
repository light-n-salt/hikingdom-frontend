import React from 'react'
import Loading from 'components/common/Loading'
import MemberList from 'components/club/MemberList'
import { useMembersDetailQuery } from 'apis/services/meetup'

type MemberModalProps = {
  clubId: number | undefined
  meetupId: string
}

function MemberModal({ clubId, meetupId }: MemberModalProps) {
  const {
    isLoading,
    isError,
    data: memberList,
  } = useMembersDetailQuery(clubId, Number(meetupId))

  console.log('memberList', memberList)

  if (isLoading || isError) {
    return <Loading />
  }

  return (
    <div>
      {memberList && (
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
