import React, { useContext } from 'react'
import styles from './UserInfo.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

import mountain from 'assets/images/mountain.png'
import time from 'assets/images/hourglass.png'
import distance from 'assets/images/shoe.png'
import height from 'assets/images/hot_air_balloon.png'

import { UserRecord } from 'types/user.interface'

type UserInfoProps = {
    userRecord: UserRecord
}

function UserInfo({ userRecord }: UserInfoProps) {
    const { theme } = useContext(ThemeContext)

    const totalMountain = userRecord.totalMountainCount.toString()
    const totalDistance = userRecord.totalDistance.toString()
    const totalAlt = userRecord.totalAlt.toString()

    return (
        <div className={`${theme} ${styles['user-info']}`}>
            <div className={styles['info-box']}>
                <Info
                    imgSrc={mountain}
                    title={'등반한 산'}
                    content={totalMountain}
                />
                <Info
                    imgSrc={time}
                    title={'시간'}
                    content={userRecord.totalDuration}
                />
            </div>
            <div className={styles['info-box']}>
                <Info
                    imgSrc={distance}
                    title={'거리(km)'}
                    content={totalDistance}
                />
                <Info imgSrc={height} title={'높이(m)'} content={totalAlt} />
            </div>
        </div>
    )
}

type InfoProps = {
    imgSrc: string
    title: string
    content: string
}

function Info({ imgSrc, title, content }: InfoProps) {
    return (
        <div className={styles.info}>
            <img src={imgSrc} />
            <div className={styles['content-box']}>
                <span className={styles.title}>{title}</span>
                <span className={styles.content}>{content}</span>
            </div>
        </div>
    )
}

export default UserInfo
