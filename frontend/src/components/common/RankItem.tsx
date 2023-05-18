import React, { useContext } from 'react'
import styles from './RankItem.module.scss'
import { FaMountain } from 'react-icons/fa'
import { useNavigate } from 'react-router-dom'
import Button from 'components/common/Button'
import IconText from 'components/common/IconText'
import marker from 'assets/images/marker.png'
import person from 'assets/images/person.png'
import hiking from 'assets/images/hiking.png'
import plan from 'assets/images/plan.png'
import asset from 'assets/images/asset.png'
import goldMedal from 'assets/images/gold_medal.png'
import bronzeMedal from 'assets/images/bronze_medal.png'
import silverMedal from 'assets/images/silver_medal.png'
import { ThemeContext } from 'styles/ThemeProvider'
import { ClubInfo } from 'types/club.interface'

type RankItemProps = {
  clubInfo: ClubInfo // 소모임 정보
  size: 'sm' | 'lg' // 크기
  onClickDeleteClub?: (clubId: number, clubName: string) => void // 삭제버튼 여부
}

function RankItem({ clubInfo, size, onClickDeleteClub }: RankItemProps) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const totalMemeber = clubInfo.totalMember.toString() + '명'

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
        <h3 className={styles.title}>
          {clubInfo.clubName.length > 9
            ? clubInfo.clubName.slice(0, 9) + '...'
            : clubInfo.clubName}
        </h3>
        {onClickDeleteClub && (
          <Button
            text="신청 취소"
            size="sm"
            color="secondary"
            onClick={onClickCancle}
          />
        )}
      </div>
      <div className={styles.info}>
        <div className={styles.flexbox}>
          <IconText imgSrc={person} text={totalMemeber} />
          {size === 'lg' && (
            <IconText imgSrc={asset} text={`${clubInfo.totalAssetCount}`} />
          )}
          <IconText imgSrc={hiking} text={`${clubInfo.totalMountainCount}`} />
          <IconText imgSrc={plan} text={`${clubInfo.totalMeetupCount}`} />
        </div>
        <IconText imgSrc={marker} text={clubInfo.location} />
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
