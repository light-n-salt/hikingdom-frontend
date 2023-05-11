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
        const nextChat = index < chats.length - 1 ? chats[index + 1] : null // 다음 채팅
        const isContinued = nextChat?.memberId === chat.memberId // memberId 비교

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
