import React, { useContext } from 'react'
import styles from './Modal.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'

interface ModalProps {
    closeModal: () => void
    children: React.ReactNode
}

function Modal({ closeModal, children }: ModalProps) {
    const { theme } = useContext(ThemeContext)

    return (
        <div onClick={closeModal} className={`${styles.modal}`}>
            <div
                onClick={(e) => e.stopPropagation()}
                className={`box ${theme}`}
            >
                {children}
            </div>
        </div>
    )
}

export default Modal
