import React, { useState } from 'react'
import { Navigate } from 'react-router-dom'

import styles from './DeleteModal.module.scss'
import Button from 'components/common/Button'

type DeleteModalProps = {
  title: string
  content?: string
  buttonText: string
  onClickDelete: () => void
  onClickCloseModal: () => void
}

function DeleteModal({title, content, buttonText, onClickDelete, onClickCloseModal}: DeleteModalProps) {
  return (
    <div className={styles.modal}>
      <div className={styles.content}>
        <h2>{title}</h2>
        {content && <span className={styles.text}>{content}</span>}
      </div>
      <div className={styles.button}>
        <Button text={buttonText} size='lg' color='primary' onClick={onClickDelete}/>
        <Button text="취소" size='lg' color='secondary' onClick={onClickCloseModal}/>
      </div>
    </div>
  )
}

export default DeleteModal