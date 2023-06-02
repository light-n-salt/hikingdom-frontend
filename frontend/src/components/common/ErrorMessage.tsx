import React from 'react'

import styles from './ErrorMessage.module.scss'

type ErrorMessageProps = {
  message: string
}

function ErrorMessage({ message }: ErrorMessageProps) {
  return (
    <div className={styles.container}>
      <p className={styles.message}>{message}</p>
    </div>
  )
}

export default ErrorMessage
