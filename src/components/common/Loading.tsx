import React from 'react'
import styles from './Loading.module.scss'
import { ReactComponent as Spinner } from 'assets/svgs/spinner.svg'
import { ReactComponent as Mountain } from 'assets/svgs/mountain.svg'

interface LoadingProps {
  type?: 'circle' | 'mountain'
  size?: 'sm' | 'md' | 'lg' // 스피너의 크기를 조절할 수 있는 속성
}

function Loading({ type = 'mountain', size = 'md' }: LoadingProps) {
  return (
    <div className={styles.container}>
      {type === 'circle' ? (
        <Spinner className={`${styles.circle} ${styles[size]}`} />
      ) : (
        <Mountain className={`${styles.mountain} ${styles[size]}`} />
      )}
    </div>
  )
}

export default Loading
