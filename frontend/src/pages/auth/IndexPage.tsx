import Button from 'components/common/Button'
import Logo from 'components/common/Logo'
import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './IndexPage.module.scss'
import mountain from 'assets/videos/mountain.mp4'

function IndexPage() {
    const navigate = useNavigate()

    function toLogin() {
        navigate('/login')
    }

    return (
        <div className={`page ${styles.container}`}>
            <Logo size="lg" />
            <Button text="시작하기" color="primary" onClick={toLogin} />
            <video className={styles.video} autoPlay loop muted>
                <source src={mountain} />
            </video>
        </div>
    )
}

export default IndexPage
