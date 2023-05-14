import React, { useContext, useState, useEffect, useRef, useMemo } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubChatPage.module.scss'
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

// import useInfiniteScroll from 'hooks/useInfiniteScroll'

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
  const [members, setMembers] = useState<{ [key: number]: ChatMember } | null>(
    null
  )
  const [chatList, setChatList] = useState<Chat[]>([])
  const [message, setMessage] = useState<string>('')
  // 멤버 정보 업데이트시 ChatItem 업데이트
  const [trigger, setTrigger] = useState<number>(0)

  // 채팅 데이터
  const {
    data: chats,
    isError,
    isLoading,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery<any>({
    queryKey: ['chats'],
    queryFn: ({ pageParam = null }) => {
      return getChats(Number(userInfo?.clubId), pageParam)
    },
    getNextPageParam: (lastPage) => {
      return lastPage.chats.hasNext
        ? lastPage.chats.content.slice(-1)[0].chatId
        : undefined
    },
    enabled: !!userInfo && !!members,
    onSuccess: (res) => {
      setChatList((chatList) => [
        ...chatList,
        ...res.pages.slice(-1)[0].chats.content,
      ])
    },
    cacheTime: 0,
  })

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
    if (!userInfo) return
    connection()
    return () => {
      // unmount시 연결 해제
      if (stomp) {
        stomp.disconnect()
      }
    }
  }, [userInfo])

  // 소켓 연결 & 구독 함수
  const connection = () => {
    const socket = new sockjs('https://hikingdom.kr/chat/ws')
    const stomp = Stomp.over(socket)
    setStomp(stomp)

    // 서버 연결
    stomp.connect(
      {
        token: localStorage.getItem('accessToken'),
      },
      () => {
        // 특정 URI 구독
        stomp.subscribe(`/sub/clubs/${userInfo?.clubId}`, (chatDTO) => {
          // 구독후 데이터를 받을 때마다 실행할 함수
          const data = JSON.parse(chatDTO.body)

          // 채팅 데이터
          if (data.type === 'MESSAGE') {
            setChatList((chatList) => [data.chat, ...chatList])
            return
          }
          // 멤버 데이터
          if (data.type === 'MEMBERS') {
            console.log('멤버 데이터 업데이트', data.members)
            setMembers(data.members)
            setTrigger((trigger) => trigger + 1)
            return
          }
        })
      }
    )
  }

  // 메세지 전송 함수
  const sendChat = () => {
    if (!message.trim()) return
    stomp.send(
      `/pub/clubs/${userInfo?.clubId}`,
      {
        headers: {
          accessToken: localStorage.getItem('accessToken'),
        },
      },
      JSON.stringify({
        type: 'chat',
        clubId: userInfo?.clubId,
        memberId: userInfo?.memberId,
        content: message,
      })
    )
    // if (infiniteRef.current) {
    //   infiniteRef.current.scrollTo({
    //     top: infiniteRef.current.scrollHeight,
    //     behavior: 'smooth',
    //   })
    // }
    setMessage('')
  }

  useScroll({
    ref: infiniteRef,
    loadMore: fetchNextPage,
    isEnd: !hasNextPage,
  })

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader title={clubInfo?.clubName} url={`/club/main`} />
      <div className={styles.content}>
        {!members || !chatList ? (
          <Loading />
        ) : (
          <ChatList
            ref1={infiniteRef}
            trigger={trigger}
            chats={chatList}
            members={members}
          />
        )}
      </div>
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
