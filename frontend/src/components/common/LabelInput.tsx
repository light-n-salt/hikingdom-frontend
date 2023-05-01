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
}

function LabelInput({
    label,
    value,
    onChange,
    isPass = false,
    isError = false,
    placeholder = '',
    type = 'text',
}: LabelInputProps) {
    const error = isError ? styles.error : ''
    const pass = isPass ? styles.pass : ''

    return (
        <div className={`${styles['label-input']}`}>
            <label htmlFor="input">{label}</label>
            <input
                id="input"
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`${error} ${pass}`}
                type={type}
            />
        </div>
    )
}

export default LabelInput
