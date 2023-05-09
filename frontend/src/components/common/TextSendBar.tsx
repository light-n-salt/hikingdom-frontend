import React from 'react'
import styles from './TextSendBar.module.scss'

import IconButton from 'components/common/IconButton'

import airplane from 'assets/images/airplane.png'

type TextSendBarProps = {
  placeholder: string
  onClick: () => void
}

function TextSendBar({ placeholder, onClick }: TextSendBarProps) {
  const onClickSubmit = () => {
    onClick()
  }
  return (
    <div className={styles.bar}>
      <input type="text" placeholder={placeholder} className={styles.input} />
      <IconButton imgSrc={airplane} size="md" onClick={onClickSubmit} />
    </div>
  )
}

export default TextSendBar
