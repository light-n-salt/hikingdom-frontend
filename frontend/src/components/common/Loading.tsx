import React from 'react'
import styles from './Loading.module.scss'
import { ReactComponent as Spinner } from 'assets/svgs/spinner.svg'
import { ReactComponent as Mountain } from 'assets/svgs/mountain.svg'

interface LoadingProps {
  type?: 'mountain' | 'circle'  // 산 모양 로딩 or 원형 로딩
  size?: 'sm' | 'md' | 'lg' // 크기
}

function Loading({ type = 'mountain', size = 'md' }: LoadingProps) {
  return (
    <>
      {type === 'circle' ? (
        <Spinner className={`${styles.circle} ${styles[size]}`} />
      ) : (
        <Mountain className={`${styles.mountain} ${styles[size]}`} />
      )}
    </>
  )
}

export default Loading
