import React from 'react'
import styles from './MemberList.module.scss'
import MemberItem from './MemberItem'
import { ClubMember } from 'types/club.interface'

type MemberListProps = {
  title: string
  length: number
  memberList: ClubMember[]
  isButton?: boolean
}

function MemberList({title, length, memberList, isButton=false}: MemberListProps) {
  return (
    <div className={styles.list}>
      <div className={styles.content}>
        <span className={styles.text}>{title}</span>
        <span className={styles.text}>{`(${length} 명)`}</span>
      </div>
      {memberList.map((memberInfo: ClubMember) => (
        <MemberItem key={memberInfo.memberId} memberInfo={memberInfo} isButton={isButton}/>
      ))}
    </div>
  )
}

export default MemberList