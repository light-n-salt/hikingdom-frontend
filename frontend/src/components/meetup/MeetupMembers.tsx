import React from 'react'
import styles from './MeetupMembers.module.scss'

import Image from 'components/common/Image'
import Button from 'components/common/Button'
import { MeetupMember } from 'types/meetup.interface'

type MeetupMembersProps = {
  memberInfo: MeetupMember[]
}

function MeetupMembers({ memberInfo }: MeetupMembersProps) {
  return (
    <div className={styles.meetup}>
      <div className={styles.titles}>
        <span className={styles.title}>참여 멤버 (N명)</span>
        <Button text="참여" color="primary" size="xs" />
      </div>
      <div className={styles.members}>
        {memberInfo.map((member) => (
          <Image
            key={member.memberId}
            imgUrl={member.profileUrl}
            size="sm"
            isSquare={true}
          />
        ))}
        <div className={styles.more}>+</div>
      </div>
    </div>
  )
}

export default MeetupMembers
