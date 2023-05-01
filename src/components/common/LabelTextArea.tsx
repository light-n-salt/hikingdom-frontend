import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './LabelTextArea.module.scss'

type LabelTextAreaProps = {
    label: string
    value: string
    placeholder: string
    onChangeText: (value: string) => void
}

function LabelTextArea({
    label,
    value,
    placeholder,
    onChangeText,
}: LabelTextAreaProps) {
    const { theme } = useContext(ThemeContext)

    const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        onChangeText(event.target.value)
    }

    return (
        <div className={`content ${theme} ${styles.labeltextarea}`}>
            <label htmlFor="textarea">{label}</label>
            <textarea
                id="textarea"
                value={value}
                onChange={handleChange}
                placeholder={placeholder}
            />
        </div>
    )
}

export default LabelTextArea
