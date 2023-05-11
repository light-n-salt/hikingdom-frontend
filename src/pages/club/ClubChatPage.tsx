import React, { useContext, useState, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import { useParams } from 'react-router-dom'
import PageHeader from 'components/common/PageHeader'
import ChatList from 'components/club/ChatList'
import Loading from 'components/common/Loading'
import { Chats, Chat, ChatMember } from 'types/chat.interface'
import { getChats, getClubSimpleInfo } from 'apis/services/clubs'
import { useQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'
import sockjs from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'
import axios from 'axios'
import TextSendBar from 'components/common/TextSendBar'

function ClubChatPage() {
  const { theme } = useContext(ThemeContext)

  // const { members, chats }: Chats = {
  //   status: 'Members',
  //   members: [
  //     {
  //       memberId: 1,
  //       nickname: '이현진진자라',
  //       profileUrl:
  //         'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',

  //       level: 3,
  //     },
  //     {
  //       memberId: 2,
  //       nickname: '예지',
  //       profileUrl:
  //         'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',

  //       level: 3,
  //     },
  //   ],
  //   chats: [
  //     {
  //       chatId: '0',
  //       memberId: 1,
  //       content: 'hi',
  //       sendAt: 'YY HH:MM:SS',
  //     },
  //     {
  //       chatId: '1',
  //       memberId: 1,

  //       content:
  //         '이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다',
  //       sendAt: 'YY HH:MM:SS',
  //     },
  //     {
  //       chatId: '2',
  //       memberId: 2,

  //       content: 'hi',
  //       sendAt: 'YY HH:MM:SS',
  //     },
  //     {
  //       chatId: '3',
  //       memberId: 2,

  //       content: 'thisisyeji',
  //       sendAt: 'YY HH:MM:SS',
  //     },
  //   ],
  // }

  const { clubId } = useParams() as { clubId: string }
  // 유저정보
  const { data: userInfo } = useUserQuery()

  // 모임정보
  const { data: clubInfo } = useQuery(['clubInfo'], () =>
    getClubSimpleInfo(parseInt(clubId))
  )

  // 소켓 통신
  const [stomp, setStomp] = useState<any>() // 타입 수정 필요

  // 채팅 & 멤버 데이터
  // useState<{ [key: number]: ChatMember }
  const [memberList, setMemberList] = useState<ChatMember[]>([])
  const [chatList, setChatList] = useState<Chat[]>([])
  const [message, setMessage] = useState<string>('')

  // 초기 데이터
  // const { isLoading, isError } = useQuery<Chats>(
  //   ['chats'],
  //   () => getChats(parseInt(clubId)),
  //   {
  //     onSuccess: (res) => {
  //       setMemberList(res.members)
  //       setChatList(res.chats)
  //     },
  //   }
  // )
  const { isLoading, isError } = useQuery(
    ['chats'],
    () => axios.get(`http://hikingdom.kr:8081/chat/clubs/1/enter`),
    {
      onSuccess: (res) => {
        setMemberList(res.data.result.members)
        setChatList(res.data.result.chats.content)
      },
    }
  )

  // mount시 통신 연결
  useEffect(() => {
    connection()
    return () => {
      // unmount시 연결 해제
      if (stomp) {
        stomp.disconnect()
      }
    }
  }, [])

  const connection = () => {
    const socket = new sockjs('http://hikingdom.kr:8081/chat')
    // console.log('socket', socket)
    const stomp = Stomp.over(socket)
    setStomp(stomp)
    // console.log('stomp', stomp)

    // 서버에 연결
    stomp.connect({}, () => {
      // 특정 URI 구독
      stomp.subscribe(`/sub/clubs/${clubId}`, (chatDTO) => {
        // 구독후 메세지를 받을 때마다 실행할 함수
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

  // 메세지 전송
  const sendChat = () => {
    if (!message.trim()) return
    stomp.send(
      `/pub/clubs/${clubId}`,
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
      <PageHeader title={clubInfo?.clubName} url={`/club/${clubId}/main`} />
      {isError || isLoading ? (
        <Loading />
      ) : (
        <ChatList chats={chatList} members={memberList} />
      )}
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
