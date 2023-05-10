import React from 'react'
import styles from './Label.module.scss'

type LabelProps = {
  label: string // 라벨 텍스트
}

function Label({ label }: LabelProps) {
  return (
    <label className={styles.label} htmlFor={`label-input-${label}`}>
      {label}
    </label>
  )
}

export default Label
