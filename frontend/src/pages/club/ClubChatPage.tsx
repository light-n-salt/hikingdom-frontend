import React, { useContext, useState, useEffect, useRef } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'
import ChatList from 'components/club/ChatList'
import Loading from 'components/common/Loading'
import { Chats, Chat, ChatMember } from 'types/chat.interface'
import { getChats, getMembers, getClubSimpleInfo } from 'apis/services/clubs'
import { useQuery, useInfiniteQuery } from '@tanstack/react-query'
import useUserQuery from 'hooks/useUserQuery'
import sockjs from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'
import TextSendBar from 'components/common/TextSendBar'
import useScroll from 'hooks/useScroll'

type InfiniteChat = {
  content: Chats[]
  hasNext: boolean
  hasPrevious: boolean
  numberOfElements: number
  pageSize: number
}

function ClubChatPage() {
  const { theme } = useContext(ThemeContext)
  const infiniteRef = useRef<HTMLDivElement>(null)
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
  const [members, setMembers] = useState<{ [key: number]: ChatMember }>({})
  const [chatList, setChatList] = useState<Chat[]>([])
  const [message, setMessage] = useState<string>('')

  // 채팅 데이터
  const { isLoading, isError } = useQuery<any>(
    ['chats'],
    () => getChats(Number(userInfo?.clubId)),
    {
      onSuccess: (res) => {
        console.log(res.chats.content.slice(-1)[0].chatId)
        setChatList(res.chats.content)
      },
      enabled: !!userInfo,
    }
  )
  // const {
  //   data: chats,
  //   isError,
  //   isLoading,
  //   fetchNextPage,
  //   hasNextPage,
  // } = useInfiniteQuery<any>({
  //   queryKey: ['chats'],
  //   queryFn: ({ pageParam = null }) => {
  //     return () => getChats(Number(userInfo?.clubId), pageParam, 20)
  //   },
  //   getNextPageParam: (lastPage) => {
  //     console.log('lastPage', lastPage)
  //     return lastPage.isLast
  //       ? lastPage.chats.content.slice(-1)[0].chatId
  //       : undefined
  //   },
  //   onSuccess: (res) => {
  //     console.log('data', res)
  //   },
  // })

  // 멤버 데이터
  const { data: memberInfo } = useQuery(
    ['members'],
    () => getMembers(Number(userInfo?.clubId)),
    {
      onSuccess: (res) => {
        setMembers(res.members)
      },
      enabled: !!userInfo,
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
    const socket = new sockjs('https://hikingdom.kr/chat')
    const stomp = Stomp.over(socket)
    setStomp(stomp)

    // 서버에 연결
    stomp.connect({}, () => {
      // 특정 URI 구독
      stomp.subscribe(`/sub/clubs/${userInfo?.clubId}`, (chatDTO) => {
        // 구독후 메세지를 받을 때마다 실행할 함수
        const data = JSON.parse(chatDTO.body)
        console.log('data', data)
        // 채팅 데이터
        if (data.type === 'MESSAGE') {
          setChatList((chatList) => [data.chat, ...chatList])
          return
        }
        // 멤버 데이터
        if (data.type === 'MEMBERS') {
          setMembers(data.members)
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
        type: 'chat',
        clubId: userInfo?.clubId,
        memberId: userInfo?.memberId,
        content: message,
      })
    )
    setMessage('')
  }

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader title={clubInfo?.clubName} url={`/club/main`} />
      {isError || isLoading ? (
        <Loading />
      ) : (
        <ChatList chats={chatList} members={members} />
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
