import React, { useContext } from 'react'
import styles from './Modal.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

type ModalProps = {
  onClick: () => void
  children: React.ReactNode
}

function Modal({ onClick, children }: ModalProps) {
  const { theme } = useContext(ThemeContext)

  return (
    <div onClick={onClick} className={`${styles.modal}`}>
      <div onClick={(e) => e.stopPropagation()} className={`content ${theme}`}>
        {children}
      </div>
    </div>
  )
}

export default Modal
