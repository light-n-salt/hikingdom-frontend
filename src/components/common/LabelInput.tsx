import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './LabelInput.module.scss'

type LabelInputProps = {
    label: string
    value: string
    placeholder: string
    onChangeText: (value: string) => void
}

function LabelInput({
    label,
    value,
    placeholder,
    onChangeText,
}: LabelInputProps) {
    const { theme } = useContext(ThemeContext)

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        onChangeText(event.target.value)
    }

    return (
        <div className={`${styles[theme]}`}>
            <label htmlFor="input">{label}</label>
            <input
                id="input"
                value={value}
                onChange={handleChange}
                placeholder={placeholder}
            />
        </div>
    )
}

export default LabelInput
