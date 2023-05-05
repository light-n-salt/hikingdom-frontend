import React from 'react'

import styles from './MemberItem.module.scss'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import LEVEL_TO_IMG from 'constants/levels'
import { convertToKm } from 'utils/convertToKm'
import { ClubMember } from 'types/club.interface'

type MemberItemProps = {
  memberInfo: ClubMember
  isButton: boolean
}

function MemberItem({ memberInfo, isButton }: MemberItemProps) {
  const imgSrc = LEVEL_TO_IMG[memberInfo.level]

  return (
    <div className={styles.container}>
      <div className={styles.user}>
        <Image
            imgUrl={memberInfo.profileUrl}
            size="md"
            isSquare={true}
        />
        <span className={styles.nickname}>{memberInfo.nickname}</span>
        <img src={imgSrc} className={styles.image}/>
      </div>
      
      <div className={styles.record}>
        <Info
          title="등산 거리(km)"
          content={convertToKm(memberInfo.totalDistance)}
        />
        
        {isButton ? (
          <div className={styles.button}>
            <Button text='거절' color='secondary' size='md' onClick={() => console.log(memberInfo.memberId, "거절")}/>
            <Button text='수락' color='primary' size='md' onClick={() => console.log(memberInfo.memberId, "수락")}/>
          </div>
        ) : (
          <>
            <Info
              title="등산 시간"
              content={`${memberInfo.totalDuration}`}
            />

            <Info
              title="등산 횟수"
              content={`${memberInfo.totalHikingCount}`}
            />
          </>
        )}
      </div>
    </div>
  )
}

type InfoProps = {
  title: string
  content: string
}

function Info({ title, content }: InfoProps) {
  return (
    <div className={styles.content}>
      <span className={styles.text}>{title}</span>
      <span className={styles.bold}>{content}</span>
    </div>
  )
}

export default MemberItem