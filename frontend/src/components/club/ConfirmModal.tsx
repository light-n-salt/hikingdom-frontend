import React, { useState } from 'react'
import { Navigate } from 'react-router-dom'

import styles from './ConfirmModal.module.scss'
import Button from 'components/common/Button'

type ConfirmModalProps = {
  title: string
  content1?: string
  content2?: string
  buttonText: string
  onClickDelete: () => void
  onClickCloseModal: () => void
}

function ConfirmModal({
  title,
  content1,
  content2,
  buttonText,
  onClickDelete,
  onClickCloseModal,
}: ConfirmModalProps) {
  return (
    <div className={styles.modal}>
      <div className={styles.content}>
        <h2>{title}</h2>
        {content1 && <span className={styles.text}>{content1}</span>}
        {content2 && <span className={styles.text}>{content2}</span>}
      </div>
      <div className={styles.button}>
        <Button
          text={buttonText}
          size="lg"
          color="primary"
          onClick={onClickDelete}
        />
        <Button
          text="취소"
          size="lg"
          color="secondary"
          onClick={onClickCloseModal}
        />
      </div>
    </div>
  )
}

export default ConfirmModal
