import React from 'react'
import { ClubInfo } from 'types/club.interface'
import { FaMountain } from 'react-icons/fa'
import person from 'assets/images/person.png'
import hourglass from 'assets/images/hourglass.png'
import shoe from 'assets/images/shoe.png'
import marker from 'assets/images/marker.png'
import styles from './RankItem.module.scss'

type RankItemProps = {
    clubInfo: ClubInfo
    size: 'sm' | 'lg'
}

function RankItem({ clubInfo, size }: RankItemProps) {
    return (
        <div className={`${styles.rankitem} ${styles[size]}`}>
            <h1>{clubInfo.clubName}</h1>
            <div>
                <IconText type="person" text={clubInfo.totalMember} />
                <IconText type="duration" text={clubInfo.totalDuration} />
                <IconText type="distance" text={clubInfo.totalDistance} />
            </div>
            <div>
                <IconText type="location" text={clubInfo.location} />
                <MtRating participationRate={clubInfo.participationRate} />
            </div>
        </div>
    )
}

export default RankItem

type IconTextProps = {
    type: 'person' | 'duration' | 'distance' | 'location'
    text: string | number
}

function IconText({ type, text }: IconTextProps) {
    const icon = {
        person: person,
        duration: hourglass,
        distance: shoe,
        location: marker,
    }[type]

    return (
        <div className={styles.icontext}>
            <img src={icon} alt="Logo" />
            <p>{text}</p>
        </div>
    )
}

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
// function Icon
// clubId: number
// clubName: string
// location: string
// totalMember: number
// totalDuration: number
// totalDistance: number
// participationRate: number
// ranking: number
