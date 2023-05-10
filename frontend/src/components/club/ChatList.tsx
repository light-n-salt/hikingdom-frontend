import React from 'react'
import styles from './ChatList.module.scss'

import ChatItem from 'components/club/ChatItem'

import { Chat, ChatMember } from 'types/chat.interface'

type ChatListProps = {
  chats: Chat[]
  members: ChatMember[]
}

function ChatList({ chats, members }: ChatListProps) {
  return (
    <div className={styles.chats}>
      {chats.map((chat, index) => {
        const prevChat = index > 0 ? chats[index - 1] : null // 이전 채팅
        const isContinued = prevChat?.memberId === chat.memberId // memberId 비교

        return (
          <ChatItem
            key={chat.chatId}
            chat={chat}
            members={members}
            isContinued={isContinued}
          />
        )
      })}
    </div>
  )
}

export default ChatList
