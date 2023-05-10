import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

import PageHeader from 'components/common/PageHeader'
import TextSendBar from 'components/common/TextSendBar'
import ChatList from 'components/club/ChatList'

import { Chats } from 'types/chat.interface'

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

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader title="모임이름" url={`/club/${clubId}/main`} />
      <ChatList chats={chats} members={members} />
      {/* <TextSendBar
        onClick={() => console.log('채팅입력@@')}
      /> */}
    </div>
  )
}

export default ClubChatPage
