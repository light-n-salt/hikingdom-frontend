import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './LabelTextArea.module.scss'

type LabelTextAreaProps = {
    label: string
    value: string
    placeholder: string
    onChange?: (event: React.ChangeEvent<HTMLTextAreaElement>) => void
    disabled?: boolean
}

function LabelTextArea({
    label,
    value,
    placeholder,
    onChange = () => {},
    disabled = false,
}: LabelTextAreaProps) {
    const { theme } = useContext(ThemeContext)

    return (
        <div className={`${styles[theme]} ${styles.labeltextarea}`}>
            <label htmlFor="textarea">{label}</label>
            <textarea
                id="textarea"
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                disabled={disabled}
            />
        </div>
    )
}

export default LabelTextArea
