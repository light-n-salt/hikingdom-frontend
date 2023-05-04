import React from 'react'
import styles from './TextButton.module.scss'

type TextButtonProps = {
  text: string
  size?: 'sm' | 'md' | 'lg'
  color: 'primary' | 'secondary' | 'tertiary' | 'white' | 'red' | 'gray'
  onClick?: () => void
  disabled?: boolean
}

export default function TextButton({
  text,
  size = 'sm',
  color,
  onClick = () => {},
  disabled = false,
}: TextButtonProps) {
  return (
    <button
      className={`${styles.button} ${styles[size]} ${styles[color]}`}
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </button>
  )
}