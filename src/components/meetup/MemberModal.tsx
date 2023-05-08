import React, { useEffect } from 'react'

import styles from './MemberModal.module.scss'
import MemberList from 'components/club/MemberList'
import { ClubMember } from 'types/club.interface'

type MemberModalProps = {
  memberList: ClubMember[]
}

function MemberModal({ memberList }: MemberModalProps) {
  console.log(memberList, ' memberList@')
  return (
    <div className={styles.modal}>
      <MemberList
        title="참여 멤버"
        length={memberList.length}
        memberList={memberList}
      />
    </div>
  )
}

export default MemberModal
