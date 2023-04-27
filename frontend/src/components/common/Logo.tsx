import React from 'react'
import styles from './Logo.module.scss'
import logoPng from 'assets/images/logo.png'

type LogoProps = {
    size?: 'sm' | 'md' | 'lg'
}

export default function Logo({ size = 'md' }: LogoProps) {
    return (
        <img
            src={logoPng}
            alt="hikingdom"
            className={`${styles.logo} ${styles[size]}`}
        />
    )
}
