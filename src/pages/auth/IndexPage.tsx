import Button from 'components/common/Button'
import Logo from 'components/common/Logo'
import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './IndexPage.module.scss'

function IndexPage() {
    const { theme } = useContext(ThemeContext)
    const navigate = useNavigate()

    function toLogin() {
        navigate('/login')
    }

    return (
        <div className={`page mobile`}>
            <div className={styles.logo}>
                <Logo />
            </div>
            <Button text="시작하기" color="primary" onClick={toLogin} />
            <video autoPlay loop muted>
                <source src="" type="video/mp4" />
            </video>
        </div>
    )
}

export default IndexPage
