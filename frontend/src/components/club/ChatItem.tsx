import React, { useContext, useState, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ChatItem.module.scss'

import Image from 'components/common/Image'

import LEVEL_TO_IMG from 'constants/levels'
import useUserQuery from 'hooks/useUserQuery'
import { Chat, ChatMember } from 'types/chat.interface'

type ChatItemProps = {
  chat: Chat
  members: ChatMember[]
  isContinued: boolean
}

type User = {
  nickname: string
  profileUrl: string
  level: string
}

function ChatItem({ chat, members, isContinued }: ChatItemProps) {
  const { theme } = useContext(ThemeContext)
  const { data: userInfo } = useUserQuery()
  const [user, setUser] = useState<User>()

  useEffect(() => {
    // 멤버ID 대조 => 해당 프로필, 닉네임 반환
    const chatMember: ChatMember | undefined = members.find(
      (member) => member.memberId === chat.memberId
    )

    if (!chatMember) return
    const { nickname, profileUrl, level } = chatMember
    const tmpUser = {
      nickname,
      profileUrl,
      level: LEVEL_TO_IMG[level],
    }
    setUser(tmpUser)
  }, [])

  // 말풍선 스타일
  const chatStyle =
    chat.memberId === userInfo?.memberId ? styles.mine : styles.others
  const imgStyle = isContinued ? styles.discontinued : ''

  // 전송 시간
  const time = chat.sendAt.split(' ')[1].split(':')

  return user ? (
    <div className={chatStyle}>
      <div className={imgStyle}>
        <Image size="sm" imgUrl={user.profileUrl} />
      </div>

      <div className={styles.chat}>
        {!isContinued && (
          <div className={styles.name}>
            <span>{user.nickname}</span>
            <img src={user.level} className={styles.level} />
          </div>
        )}
        <div className={styles.content}>
          <div className={`${styles.chatContent} ${styles[`${theme}`]}`}>
            {chat.content}
          </div>
          <div className={styles.time}>{`${time[0]}:${time[1]}`}</div>
        </div>
      </div>
    </div>
  ) : (
    <div></div>
  )
}

export default ChatItem
