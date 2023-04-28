import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './LabelInput.module.scss'

type LabelInputProps = {
    label: string
    value: string
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    isError?: boolean
    placeholder?: string
    type?: string
}

function LabelInput({
    label,
    value,
    onChange,
    isError = false,
    placeholder = '',
    type = 'text',
}: LabelInputProps) {
    const { theme } = useContext(ThemeContext)

    const error = isError ? styles.error : ''

    return (
        <div className={`${styles[theme]} ${styles['label-input']}`}>
            <label htmlFor="input">{label}</label>
            <input
                id="input"
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`${error}`}
                type={type}
            />
        </div>
    )
}

export default LabelInput
