import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './Main.module.scss'
import Logo from 'components/common/Logo'
import Modal from 'components/common/Modal'

export default function Main() {
    const [isModal, setIsModal] = useState(false)
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <>
            <button
                onClick={() => {
                    setIsModal(true)
                }}
            ></button>
            {isModal && (
                <Modal
                    closeModal={() => {
                        setIsModal(false)
                    }}
                >
                    <Logo />
                </Modal>
            )}
        </>
    )
}
