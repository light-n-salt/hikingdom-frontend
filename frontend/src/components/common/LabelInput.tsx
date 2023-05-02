import React, { useContext } from 'react'
import styles from './LabelInput.module.scss'

type LabelInputProps = {
    label: string
    value: string
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    isPass?: boolean
    isError?: boolean
    placeholder?: string
    type?: string
    disabled?: boolean
}

function LabelInput({
    label,
    value,
    onChange,
    isPass = false,
    isError = false,
    placeholder = '',
    type = 'text',
    disabled = false,
}: LabelInputProps) {
    const error = isError ? styles.error : ''
    const pass = isPass ? styles.pass : ''

    return (
        <div className={styles.container}>
            <label className={styles.label} htmlFor={`label-input-${label}`}>
                {label}
            </label>
            <input
                id={`label-input-${label}`}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`${styles.input} ${error} ${pass}`}
                type={type}
                disabled={disabled}
            />
        </div>
    )
}

export default LabelInput
