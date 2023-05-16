import React from 'react'
import styles from './MemberItem.module.scss'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import LEVEL_TO_IMG from 'constants/levels'
import { convertToKm } from 'utils/convertToKm'
import { ClubMember } from 'types/club.interface'
import { convertToTime } from 'utils/convertToTime'

type MemberItemProps = {
  memberInfo: ClubMember
  onClickJoin?: (params: number) => void
  onClickDelete?: (params: number) => void
}

function MemberItem({
  memberInfo,
  onClickJoin,
  onClickDelete,
}: MemberItemProps) {
  const imgSrc = LEVEL_TO_IMG[memberInfo.level]

  return (
    <div className={styles.container}>
      <div className={styles.user}>
        <Image imgUrl={memberInfo.profileUrl} size="sm" isSquare={true} />
        <div className={styles.username}>
          <span>{memberInfo.nickname}</span>
          <img src={imgSrc} className={styles.image} />
        </div>
      </div>

      <div className={styles.record}>
        <Info
          title="등산 거리(km)"
          content={`${(memberInfo.totalDistance / 1000).toFixed()}`}
        />

        {onClickJoin && onClickDelete ? (
          <div className={styles.button}>
            <Button
              text="거절"
              color="secondary"
              size="xs"
              onClick={() => onClickDelete(memberInfo.memberId)}
            />
            <Button
              text="수락"
              color="primary"
              size="xs"
              onClick={() => onClickJoin(memberInfo.memberId)}
            />
          </div>
        ) : (
          <Info
            title="등산 시간(H)"
            content={`${(memberInfo.totalDuration / 60).toFixed()}`}
          />
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
