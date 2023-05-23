import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import styles from './MemberItem.module.scss'
import Image from 'components/common/Image'
import Button from 'components/common/Button'
import LEVEL_TO_IMG from 'constants/levels'
import { ClubMember } from 'types/club.interface'

import { ThemeContext } from 'styles/ThemeProvider'
import thousandSeparator from 'utils/thousandSeparator'
import host from 'assets/images/host.png'

type MemberItemProps = {
  memberInfo: ClubMember
  hostId?: number
  onClickJoin?: (params: number) => void
  onClickDelete?: (params: number) => void
}

function MemberItem({
  memberInfo,
  onClickJoin,
  onClickDelete,
  hostId,
}: MemberItemProps) {
  const navigate = useNavigate()
  const imgSrc = LEVEL_TO_IMG[memberInfo.level]
  const { theme } = useContext(ThemeContext)

  const isHost = memberInfo.memberId === hostId ? null : styles.hidden

  return (
    <div className={`${styles.container} ${styles[theme]}`}>
      <div
        className={styles.user}
        onClick={() => navigate(`/profile/${memberInfo.nickname}`)}
      >
        <Image imgUrl={memberInfo.profileUrl} size="sm" isSquare={true} />
        <img className={`${styles.hostImg} ${isHost}`} src={host}/>
        <div className={styles.username}>
          <span>{memberInfo.nickname}</span>
          <img src={imgSrc} className={styles.level} />
        </div>
      </div>

      <div className={styles.flexbox}>
        <Info
          title="총 거리(km)"
          content={`${thousandSeparator((memberInfo.totalDistance / 1000).toFixed())}`}
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
            title="총시간(h)"
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
    <div className={styles.record}>
      <span className={styles.title}>{title}</span>
      <span className={styles.content}>{content}</span>
    </div>
  )
}

export default MemberItem
