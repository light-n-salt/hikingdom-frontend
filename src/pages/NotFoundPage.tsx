import React, { useContext } from 'react'
import styles from './NotFoundPage.module.scss'
import { useNavigate } from 'react-router-dom'
import Button from 'components/common/Button'
import { ThemeContext } from 'styles/ThemeProvider'

function NotFoundPage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  return (
    <div className={`page ${theme}`}>
      <div className={styles.content}>
        <div>
          <p>404 Page Not Found</p>
        </div>
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
