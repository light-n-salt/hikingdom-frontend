import React, { useContext } from 'react'
import { ClubInfo } from 'types/club.interface'
import { FaMountain } from 'react-icons/fa'
import person from 'assets/images/person.png'
import hourglass from 'assets/images/hourglass.png'
import shoe from 'assets/images/shoe.png'
import marker from 'assets/images/marker.png'
import styles from './RankItem.module.scss'
import IconText from './IconText'
import Button from './Button'
import { ThemeContext } from 'styles/ThemeProvider'
import bronzeMedal from 'assets/images/bronze_medal.png'
import silverMedal from 'assets/images/silver_medal.png'
import goldMedal from 'assets/images/gold_medal.png'

type RankItemProps = {
  clubInfo: ClubInfo
  size: 'sm' | 'lg'
  isButton?: boolean
}

function RankItem({ clubInfo, size, isButton = false }: RankItemProps) {
  const { theme } = useContext(ThemeContext)

  const totalMemeber = clubInfo.totalMember.toString() + '명'
  const totalDistance = clubInfo.totalDistance.toString() + 'km'

  function onClickDeleteClub() {
    console.log(clubInfo.clubId)
  }

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

  return (
    <div className={`content ${theme} ${styles['rank-item']} ${styles[size]}`}>
      <div className={styles.header}>
        <span className={styles.title}>{clubInfo.clubName}</span>
        {isButton && (
          <Button
            text="신청 취소"
            size="sm"
            color="secondary"
            onClick={onClickDeleteClub}
          />
        )}
      </div>
      <div className={`${styles['icon-info-box']} ${styles[size]}`}>
        <IconText imgSrc={person} text={totalMemeber} />
        <div className={styles['flex-box']}>
          <IconText imgSrc={hourglass} text={clubInfo.totalDuration} />
          <IconText imgSrc={shoe} text={totalDistance} />
        </div>
      </div>
      <div className={styles['flex-box']}>
        {size === 'lg' && <IconText imgSrc={marker} text={clubInfo.location} />}
        <MtRating participationRate={clubInfo.participationRate} />
      </div>
      {rankingIcon ? (
        <img className={styles['medal']} src={rankingIcon} />
      ) : (
        <div className={styles['rank-box']}>{clubInfo.ranking}</div>
      )}
    </div>
  )
}

export default RankItem

type MtRatingProps = {
  participationRate: number
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
