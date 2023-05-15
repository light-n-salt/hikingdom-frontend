import React from 'react'
import styles from './MemberList.module.scss'
import MemberItem from './MemberItem'
import { ClubMember } from 'types/club.interface'

type MemberListProps = {
  title: string
  length: number
  memberList: ClubMember[]
  onClickJoin?: (params: number) => void
  onClickDelete?: (params: number) => void
}

function MemberList({
  title,
  length,
  memberList,
  onClickJoin,
  onClickDelete,
}: MemberListProps) {
  return (
    <div className={styles.list}>
      <div className={styles.content}>
        <span className={styles.text}>{title}</span>
        <span className={styles.text}>{`(${length} 명)`}</span>
      </div>
      {memberList.map((memberInfo: ClubMember) => (
        <MemberItem
          key={memberInfo.memberId}
          memberInfo={memberInfo}
          onClickJoin={onClickJoin}
          onClickDelete={onClickDelete}
        />
      ))}
    </div>
  )
}

export default MemberList
