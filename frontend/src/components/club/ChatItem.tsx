import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ChatItem.module.scss'

import Image from 'components/common/Image'

import LEVEL_TO_IMG from 'constants/levels'

import { Chat } from 'types/chat.interface'

type ChatItemProps = {
  chat: Chat
}

function ChatItem({ chat }: ChatItemProps) {
  const { theme } = useContext(ThemeContext)
  const levelSrc = LEVEL_TO_IMG[chat.level]

  const username = '정예지'
  const isMine = chat.nickname === username
  const chatStyle = isMine ? styles.mine : styles.others

  return (
    <div className={chatStyle}>
      {!isMine && <Image size="sm" imgUrl={chat.profileUrl} />}
      <div className={styles.chat}>
        {!isMine && (
          <div className={styles.name}>
            <span>{chat.nickname}</span>
            <img src={levelSrc} className={styles.level} />
          </div>
        )}
        <div className={`${styles.content} ${theme} content`}>
          {chat.content}
        </div>
      </div>
    </div>
  )
}

export default ChatItem
