import React, { useContext } from 'react'
import styles from './NotFoundPage.module.scss'
import { useNavigate } from 'react-router-dom'
import Button from 'components/common/Button'
import { ThemeContext } from 'styles/ThemeProvider'
import { ReactComponent as Cloud } from 'assets/svgs/cloud.svg'
import { ReactComponent as Mountain } from 'assets/svgs/lake_mountain.svg'
function NotFoundPage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  return (
    <div className={`page ${theme} ${styles.page}`}>
      <div className={styles.clouds}>
        <Cloud className={styles.cloud1} />
        <Cloud className={styles.cloud2} />
        <Cloud className={styles.cloud3} />
        <Cloud className={styles.cloud4} />
        <Cloud className={styles.cloud5} />
      </div>
      <div className={styles.content}>
        <p>요청하신 페이지를 찾을 수 없습니다</p>

        <Button
          text="메인으로"
          size="md"
          color="secondary"
          onClick={() => navigate('/main')}
        />
      </div>
      <Mountain className={styles.mountain} />
    </div>
  )
}

export default NotFoundPage
