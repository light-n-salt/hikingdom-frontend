import React, { useContext, useState, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

import PageHeader from 'components/common/PageHeader'
import TextSendBar from 'components/common/TextSendBar'
import ChatList from 'components/club/ChatList'
import { Chats, Chat, ChatMember } from 'types/chat.interface'

import { useQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'
import sockjs from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

function ClubChatPage() {
  const { theme } = useContext(ThemeContext)
  const clubId = 1

  const { members, chats }: Chats = {
    status: 'Members',
    members: [
      {
        memberId: 1,
        nickname: '이현진진자라',
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',

        level: 3,
      },
      {
        memberId: 2,
        nickname: '예지',
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',

        level: 3,
      },
    ],
    chats: [
      {
        chatId: '0',
        memberId: 1,
        content: 'hi',
        sendAt: 'YY HH:MM:SS',
      },
      {
        chatId: '1',
        memberId: 1,

        content:
          '이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다',
        sendAt: 'YY HH:MM:SS',
      },
      {
        chatId: '2',
        memberId: 2,

        content: 'hi',
        sendAt: 'YY HH:MM:SS',
      },
      {
        chatId: '3',
        memberId: 2,

        content: 'thisisyeji',
        sendAt: 'YY HH:MM:SS',
      },
    ],
  }

  // 유저정보
  const { data: userInfo } = useUserQuery()

  // 소켓 통신
  const [stomp, setStomp] = useState<any>() // 타입 수정 필요

  // 채팅 & 멤버 데이터
  // useState<{ [key: number]: ChatMember }
  const [memberList, setMemberList] = useState<ChatMember[]>([])
  const [chatList, setChatList] = useState<Chat[]>([])
  const [message, setMessage] = useState<string>('')

  // 초기 데이터 불러오기
  useEffect(() => {
    //useQuery
  }, [])

  // 통신 연결
  useEffect(() => {}, [])

  function connection() {
    const socket = new sockjs('연결할 url')
    const stomp = Stomp.over(socket)
    setStomp(stomp)

    // 서버에 connect 프레임 전송
    stomp.connect({}, () => {
      // 특정 URI 구독
      stomp.subscribe(`연결할 url`, (chatDTO) => {
        // 구독 이후 메세지를 받을 때마다 실행할 콜백함수
        const data = JSON.parse(chatDTO.body)

        // 채팅 데이터
        if (data.status === 'chat') {
          const { chatId, memberId, content, sendAt } = data
          setChatList((chatList) => [
            { chatId, memberId, content, sendAt },
            ...chatList,
          ])
          return
        }

        // 멤버 데이터
        if (data.status === 'member') {
          const { memberId, nickname, profileUrl, level } = data
          setMemberList((memberList) => [
            { memberId, nickname, profileUrl, level },
            ...memberList,
          ])
          return
        }
      })
    })
  }

  // 채팅 전송
  function sendChat() {
    if (!message.trim()) return
    stomp.send(
      'url',
      {},
      JSON.stringify({
        status: 'chat',
        memberId: userInfo?.memberId,
        content: message,
      })
    )
  }

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader title="모임이름" url={`/club/${clubId}/main`} />
      <ChatList chats={chatList} members={memberList} />
      <TextSendBar
        placeholder="내용을 입력해주세요"
        content={message}
        setContent={setMessage}
        onClick={sendChat}
      />
    </div>
  )
}

export default ClubChatPage
