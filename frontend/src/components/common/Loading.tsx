import React from 'react'
import { ReactComponent as Spinner } from 'assets/images/spinner.svg'
import styles from './Loading.module.scss'

interface LoadingProps {
    size?: 'sm' | 'md' | 'lg' // 스피너의 크기를 조절할 수 있는 속성
}

function Loading({ size = 'md' }: LoadingProps) {
    return (
        <div className={styles.container}>
            <Spinner className={`${styles.spinner} ${styles[size]}`} />
        </div>
    )
}

export default Loading
