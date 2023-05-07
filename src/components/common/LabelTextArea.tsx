import React from 'react'
import styles from './LabelTextArea.module.scss'

type LabelTextAreaProps = {
  label: string // 라벨 텍스트
  value: string // textarea 태그의 value
  placeholder?: string // textarea 태그의 placeholder
  size?: 'sm' | 'md' | 'lg' // textarea 사이즈
  onChange?: (event: React.ChangeEvent<HTMLTextAreaElement>) => void // textarea의 input 변경시 동작할 함수
  disabled?: boolean // textarea 태그 disabled
}

function LabelTextArea({
  label,
  value,
  placeholder = '',
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
