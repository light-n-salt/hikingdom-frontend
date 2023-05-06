import React from 'react'
import styles from './ChatList.module.scss'

import ChatItem from 'components/club/ChatItem'

import { Chat } from 'types/chat.interface'

type ChatListProps = {
  chatting: Chat[]
}

function ChatList({ chatting }: ChatListProps) {
  return (
    <div className={styles.chats}>
      {chatting.map((chat) => (
        <ChatItem key={chat.chattingId} chat={chat} />
      ))}
    </div>
  )
}

export default ChatList
