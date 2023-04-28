import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './LabelInput.module.scss'

type LabelInputProps = {
    label: string
    value: string
    placeholder: string
    isError: boolean
    onChangeText: (value: string) => void
}

function LabelInput({
    label,
    value,
    placeholder,
    onChangeText,
    isError,
}: LabelInputProps) {
    const { theme } = useContext(ThemeContext)

    const error = isError ? styles.error : ''

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onChangeText(event.target.value)
    }

    return (
        <div className={`${styles[theme]} ${styles.labelinput}`}>
            <label htmlFor="input">{label}</label>
            <input
                id="input"
                value={value}
                onChange={handleChange}
                placeholder={placeholder}
                className={`${error}`}
            />
        </div>
    )
}

export default LabelInput
