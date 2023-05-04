import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './CheckBox.module.scss'

type CheckBoxProps = {
  id: string
  label: string
  isChecked: boolean
  onClick: () => void
}

function CheckBox({ id, label, isChecked, onClick }: CheckBoxProps) {
  return (
    <div className={styles.container}>
      <input
        id={`checkbox-${id}`}
        type="checkbox"
        checked={isChecked}
        onClick={onClick}
        className={styles.input}
      />
      <label htmlFor={`checkbox-${id}`} className={styles.label}>
        {label}
      </label>
    </div>
  )
}

export default CheckBox
