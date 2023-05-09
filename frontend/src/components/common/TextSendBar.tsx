import React from 'react'
import styles from './TextSendBar.module.scss'

import IconButton from 'components/common/IconButton'

import airplane from 'assets/images/airplane.png'

type TextSendBarProps = {
  placeholder: string
  content: string
  setContent: (params: string) => void
  onClick: () => void
}

function TextSendBar({
  placeholder,
  content,
  setContent,
  onClick,
}: TextSendBarProps) {
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setContent(e.target.value)
  }

  return (
    <div className={styles.bar}>
      <input
        onChange={onChange}
        type="text"
        value={content}
        placeholder={placeholder}
        className={styles.input}
      />
      <IconButton imgSrc={airplane} size="md" onClick={onClick} />
    </div>
  )
}

export default TextSendBar
