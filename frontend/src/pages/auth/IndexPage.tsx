import React from 'react'
import styles from './IndexPage.module.scss'
import { useNavigate } from 'react-router-dom'
import mountainVideo from 'assets/videos/mountain.mp4'
import Logo from 'components/common/Logo'
import Button from 'components/common/Button'

function IndexPage() {
  const navigate = useNavigate()

  return (
    <>
      <div className={`page p-lg ${styles.container}`}>
        <Logo size="lg" />
        <Button
          text="시작하기"
          color="primary"
          size="lg"
          onClick={() => navigate('/login')}
        />
      </div>
      <video className={styles.video} autoPlay loop muted>
        <source src={mountainVideo} />
      </video>
    </>
  )
}

export default IndexPage
