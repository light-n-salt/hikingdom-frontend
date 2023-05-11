import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtTitle.module.scss'
import { convertToHour } from 'utils/convertToTime'

type MtTitleProps = {
  name: string
  maxAlt: number
  timeDuration: number
  assetUrl: string
}

function MtTitle({ name, maxAlt, timeDuration, assetUrl }: MtTitleProps) {
  const { theme } = useContext(ThemeContext)
  return (
    <div className={`content ${theme} ${styles.mttitle}`}>
      <div className={styles.title}>{name}</div>
      <div className={styles.container}>
        {/* 높이 */}
        <div className={styles.content}>
          <span className={styles.bold}>{Math.floor(maxAlt)}m</span>
          <span className={styles.text}>높이</span>
        </div>

        <span className={styles.border}>|</span>

        {/* 왕복시간 */}
        <div className={styles.content}>
          <span className={styles.bold}>{convertToHour(timeDuration)}</span>
          <span className={styles.text}>왕복시간</span>
        </div>

        <span className={styles.border}>|</span>

        {/* asset */}
        <div className={styles.content}>
          <img src={assetUrl} className={styles.img} />
        </div>
      </div>
    </div>
  )
}

export default MtTitle
