import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtContent.module.scss'

import IconText from 'components/common/IconText'

import peaksImg from 'assets/images/mountain.png'
import addressImg from 'assets/images/marker.png'
import transportImg from 'assets/images/transport.png'
import facilityImg from 'assets/images/facility.png'
import descriptionImg from 'assets/images/message.png'

type MtContentProps = {
    address: string,
    peaks: string[],
    transport: string,
    facility: string, 
    description: string,
}

function MtContent({address, peaks, transport, facility, description}: MtContentProps) {
    const { theme, toggleTheme } = useContext(ThemeContext)
    return (
        <div className={`content ${theme} ${styles.mtcontent}`}>
            <Info
                imgSrc={addressImg}
                title="주소"
                content={address}
            />
            <Info
                imgSrc={peaksImg}
                title="봉우리"
                content={peaks[0]}
            />
            <Info
                imgSrc={transportImg}
                title="지하철역"
                content={transport}
            />
            <Info
                imgSrc={facilityImg}
                title="편의시설"
                content={facility}
            />
            <div className={styles.description}>
                <IconText
                    imgSrc={descriptionImg}
                    text="설명"
                    size="md"
                    isBold={true}
                />
                <span className={styles.text}>{description}</span>
            </div>
        </div>
    ) 
}

type InfoProps = {
    imgSrc: string,
    title: string,
    content: string,
}

function Info({imgSrc, title, content}: InfoProps) {
    return (
        <div className={styles.content}>
            <IconText
                imgSrc={imgSrc}
                text={title}
                size="md"
                isBold={true}
            />
            <span className={styles.text}>{content}</span>
        </div>
    )
}

export default MtContent
