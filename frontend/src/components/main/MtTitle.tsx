import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtTitle.module.scss'

type MtTitleProps = {
  name: string
  maxAlt: number
  timeDuration: number
  assetUrl: string
}

function MtTitle({ name, maxAlt, timeDuration, assetUrl }: MtTitleProps) {
  const { theme, toggleTheme } = useContext(ThemeContext)
  return (
    <div className={`content ${theme} ${styles.mttitle}`}>
      <div className={styles.title}>{name}</div>
      <div className={styles.container}>
        {/* 높이 */}
        <div className={styles.content}>
          <span className={styles.bold}>{maxAlt}m</span>
          <span className={styles.text}>높이</span>
        </div>

        <span className={styles.border}>|</span>

        {/* 왕복시간 */}
        <div className={styles.content}>
          <span className={styles.bold}>약 {timeDuration / 60}시간</span>
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
