import React, { useContext } from 'react'
import styles from './NotFoundPage.module.scss'
import { useNavigate } from 'react-router-dom'
import Button from 'components/common/Button'
import { ThemeContext } from 'styles/ThemeProvider'
import { ReactComponent as NotFound } from 'assets/svgs/notFound.svg'

function NotFoundPage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  return (
    <div className={`page ${theme} ${styles.page}`}>
      <div className={styles.content}>
        <NotFound className={styles.star1} />
        <NotFound className={styles.star2} />
        <NotFound className={styles.star3} />
        <NotFound className={styles.star4} />
        <p>요청하신 페이지를 찾을 수 없습니다</p>
        <Button
          text="메인으로"
          size="md"
          color="secondary"
          onClick={() => navigate('/main')}
        />
      </div>
    </div>
  )
}

export default NotFoundPage
