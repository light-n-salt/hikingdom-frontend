import React from 'react'
import styles from './LabelTextArea.module.scss'

type LabelTextAreaProps = {
  label: string
  value: string
  placeholder: string
  size?: 'sm' | 'md' | 'lg'
  onChange?: (event: React.ChangeEvent<HTMLTextAreaElement>) => void
  disabled?: boolean
}

function LabelTextArea({
  label,
  value,
  placeholder,
  size = 'md',
  onChange = () => {},
  disabled = false,
}: LabelTextAreaProps) {
  return (
    <div className={styles.container}>
      <label className={styles.label} htmlFor="textarea">
        {label}
      </label>
      <textarea
        id="textarea"
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        className={`${styles.textarea} ${styles[size]}`}
      />
    </div>
  )
}

export default LabelTextArea
