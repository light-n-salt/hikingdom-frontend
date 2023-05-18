import React from 'react'
import MemberList from 'components/club/MemberList'
import { ClubMember } from 'types/club.interface'

type MemberModalProps = {
  memberList: ClubMember[]
}

function MemberModal({ memberList }: MemberModalProps) {
  return (
    <div>
      <MemberList
        title="참여 멤버"
        length={memberList.length}
        memberList={memberList}
      />
    </div>
  )
}

export default MemberModal
