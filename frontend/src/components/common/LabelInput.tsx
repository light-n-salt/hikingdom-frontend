import React from 'react'
import styles from './LabelInput.module.scss'

type LabelInputProps = {
  label: string // 라벨 텍스트
  value: string // input태그 value
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void // input 변화시 동작할 함수
  isPass?: boolean // pass 여부 -> border 초록색
  isError?: boolean // error 여부 -> border 빨간색
  placeholder?: string // placeholder
  type?: string // input 태그 type
  disabled?: boolean // input 태그 dsiabled
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
