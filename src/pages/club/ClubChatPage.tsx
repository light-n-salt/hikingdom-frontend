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
  const { data: userInfo } = useUserQuery() // 유저 정보

  // 모임정보
  const { data: clubInfo } = useQuery(
    ['clubInfo'],
    () => getClubSimpleInfo(Number(userInfo?.clubId)),
    {
      enabled: !!userInfo,
    }
  )

  // 소켓 통신
  const [stomp, setStomp] = useState<any>() // 타입 수정 필요

  // 채팅 & 멤버 데이터
  // useState<{ [key: number]: ChatMember }
  const [memberList, setMemberList] = useState<ChatMember[]>([])
  const [chatList, setChatList] = useState<Chat[]>([])
  const [message, setMessage] = useState<string>('')

  // 초기 데이터 Todo: https로 변경
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
    () =>
      axios.get(
        `http://hikingdom.kr:8081/chat/clubs/${userInfo?.clubId}/chats?size=20`
      ),
    {
      onSuccess: (res) => {
        // console.log(res.data.result.chats.content)
        setChatList(res.data.result.chats.content)
      },
    }
  )

  const { data: memberInfo } = useQuery(
    ['members'],
    () =>
      axios.get(
        `http://hikingdom.kr:8081/chat/clubs/${userInfo?.clubId}/members`
      ),
    {
      onSuccess: (res) => {
        setMemberList(res.data.result.members)
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
    console.log('socket', socket)
    const stomp = Stomp.over(socket)
    setStomp(stomp)

    // 서버에 연결
    stomp.connect({}, () => {
      // 특정 URI 구독
      stomp.subscribe(`/sub/clubs/${userInfo?.clubId}`, (chatDTO) => {
        // 구독후 메세지를 받을 때마다 실행할 함수
        const data = JSON.parse(chatDTO.body)

        // 채팅 데이터
        if (data.type === 'MESSAGE') {
          setChatList((chatList) => [data.chat, ...chatList])
          return
        }
        // 멤버 데이터
        if (data.type === 'MEMBERS') {
          const { memberId, nickname, profileUrl, level } = data.member
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
      `/pub/clubs/${userInfo?.clubId}`,
      {},
      JSON.stringify({
        status: 'chat',
        clubId: userInfo?.clubId,
        memberId: userInfo?.memberId,
        content: message,
      })
    )
    setMessage('')
  }

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader
        title={clubInfo?.clubName}
        url={`/club/${userInfo?.clubId}/main`}
      />
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
