import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './CheckBox.module.scss'

type CheckBoxProps = {
    label: string
    isChecked: boolean
    setIsChecked: (value: boolean) => void
}

function CheckBox({ label, isChecked, setIsChecked }: CheckBoxProps) {
    const { theme } = useContext(ThemeContext)

    return (
        <div className={`content ${theme} ${styles.checkbox}`}>
            <input
                type="checkbox"
                checked={isChecked}
                onClick={() => setIsChecked(!isChecked)}
            />
            <label htmlFor="input">{label}</label>
        </div>
    )
}

export default CheckBox
