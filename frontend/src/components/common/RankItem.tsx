import React, { useContext } from 'react'
import styles from './RankItem.module.scss'
import { FaMountain } from 'react-icons/fa'
import { useNavigate } from 'react-router-dom'
import Button from 'components/common/Button'
import IconText from 'components/common/IconText'
import shoe from 'assets/images/shoe.png'
import marker from 'assets/images/marker.png'
import person from 'assets/images/person.png'
import hourglass from 'assets/images/hourglass.png'
import goldMedal from 'assets/images/gold_medal.png'
import bronzeMedal from 'assets/images/bronze_medal.png'
import silverMedal from 'assets/images/silver_medal.png'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'
import { convertToTime } from 'utils/convertToTime'

type RankItemProps = {
  clubInfo: ClubInfo // 소모임 정보
  size: 'sm' | 'lg' // 크기
  onClickDeleteClub?: (clubId: number, clubName: string) => void // 삭제버튼 여부
}

function RankItem({ clubInfo, size, onClickDeleteClub }: RankItemProps) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const totalMemeber = clubInfo.totalMember.toString() + '명'
  const totalDistance = clubInfo.totalDistance.toString() + 'km'

  // 랭킹 아이콘
  let rankingIcon = null
  switch (clubInfo.ranking) {
    case 1:
      rankingIcon = goldMedal
      break
    case 2:
      rankingIcon = silverMedal
      break
    case 3:
      rankingIcon = bronzeMedal
      break
  }

  // 소모임 신청 취소 함수
  function onClickCancle(e: React.TouchEvent | React.MouseEvent) {
    e.stopPropagation()
    onClickDeleteClub && onClickDeleteClub(clubInfo.clubId, clubInfo.clubName)
  }

  return (
    <div
      className={`content ${theme} ${styles.container} ${styles[size]}`}
      onClick={() => navigate(`/club/${clubInfo.clubId}/detail`)}
    >
      <div className={styles.header}>
        <span className={styles.title}>
          {clubInfo.clubName.length > 9
            ? clubInfo.clubName.slice(0, 9) + '...'
            : clubInfo.clubName}
        </span>
        {onClickDeleteClub && (
          <Button
            text="신청 취소"
            size="sm"
            color="secondary"
            onClick={onClickCancle}
          />
        )}
      </div>
      <div className={`${styles.info} ${styles[size]}`}>
        <IconText imgSrc={person} text={totalMemeber} />
        <div className={styles.flexbox}>
          <IconText
            imgSrc={hourglass}
            text={convertToTime(clubInfo.totalDuration)}
          />
          <IconText imgSrc={shoe} text={totalDistance} />
        </div>
      </div>
      <div className={styles.flexbox}>
        {size === 'lg' && <IconText imgSrc={marker} text={clubInfo.location} />}
        <MtRating participationRate={clubInfo.participationRate} />
      </div>
      {/* 랭킹 아이콘 */}
      {rankingIcon ? (
        <img className={styles.medal} src={rankingIcon} />
      ) : clubInfo.ranking ? (
        <div className={styles.rank}>{clubInfo.ranking}</div>
      ) : (
        <div className={styles.rank}>
          <FaMountain />
        </div>
      )}
    </div>
  )
}

export default RankItem

// 참여도에 따른 산모양 5점 별점 아이콘 컴포넌트 반환
type MtRatingProps = {
  participationRate: number // 참여도
}

function MtRating({ participationRate }: MtRatingProps) {
  const score = Math.round(participationRate / 20)

  return (
    <div className={styles['mt-rating']}>
      <p>참여도</p>
      <div>
        {[...Array(5)].map((_, index) => (
          <FaMountain
            key={`fa-mountain-${index}`}
            className={index < score ? styles['green'] : styles['gray']}
          />
        ))}
      </div>
    </div>
  )
}
