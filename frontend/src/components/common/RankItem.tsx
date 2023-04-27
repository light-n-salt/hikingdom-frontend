import React, { useContext } from 'react'
import { ClubInfo } from 'types/club.interface'
import { FaMountain } from 'react-icons/fa'
import person from 'assets/images/person.png'
import hourglass from 'assets/images/hourglass.png'
import shoe from 'assets/images/shoe.png'
import marker from 'assets/images/marker.png'
import styles from './RankItem.module.scss'
import IconText from './IconText'
import { ThemeContext } from 'styles/ThemeProvider'

type RankItemProps = {
    clubInfo: ClubInfo
    size: 'sm' | 'lg'
}

function RankItem({ clubInfo, size }: RankItemProps) {
    const { theme } = useContext(ThemeContext)

    const totalMemeber = clubInfo.totalMember.toString()
    const totalDistance = clubInfo.totalDistance.toString() + 'km'

    return (
        <div className={`box ${theme} ${styles.rankitem} ${styles[size]}`}>
            <h3>{clubInfo.clubName}</h3>
            <div>
                <IconText imgSrc={person} text={totalMemeber} />
                <IconText imgSrc={hourglass} text={clubInfo.totalDuration} />
                <IconText imgSrc={shoe} text={totalDistance} />
            </div>
            <div>
                {size === 'lg' && (
                    <IconText imgSrc={marker} text={clubInfo.location} />
                )}
                <MtRating participationRate={clubInfo.participationRate} />
            </div>
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
        <div className={styles.mtrating}>
            <p>참여도</p>
            <div>
                {[...Array(5)].map((_, index) => (
                    <FaMountain
                        key={`fa-mountain-${index}`}
                        className={
                            index < score ? styles['green'] : styles['gray']
                        }
                    />
                ))}
            </div>
        </div>
    )
}
