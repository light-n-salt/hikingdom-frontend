import React, { useContext } from 'react'
import styles from './Modal.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

type ModalProps = {
  onClick: () => void // 닫는 함수
  children: React.ReactNode // 안에 띄울 컨텐츠
}

function Modal({ onClick, children }: ModalProps) {
  const { theme } = useContext(ThemeContext)

  return (
    <div onClick={onClick} className={`${styles.modal}`}>
      <div
        onClick={(e) => e.stopPropagation()}
        className={`content ${theme} ${styles.content}`}
      >
        {children}
      </div>
    </div>
  )
}

export default Modal
