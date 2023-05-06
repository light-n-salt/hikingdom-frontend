import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'

import PageHeader from 'components/common/PageHeader'
import TextSendBar from 'components/common/TextSendBar'
import ChatList from 'components/club/ChatList'

import { Chats } from 'types/chat.interface'

function ClubChatPage() {
  const { theme } = useContext(ThemeContext)
  const clubId = 1

  const result: Chats = {
    groupId: 1,
    groupName: '그룹이름',
    chatting: [
      {
        chattingId: 0,
        memberId: 1,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '이현진진자라',
        level: 3,
        content:
          '이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다이건 내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 1,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content: '이건 내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 1,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content: '이건 내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 2,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content: '이건 내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
      {
        chattingId: 3,
        memberId: 2,
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        nickname: '정예지',
        level: 3,
        content:
          '이건 내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다내용입니다',
        sendAt: 'HH:MM:SS',
        isContinue: true,
      },
    ],
  }

  return (
    <div className={`page p-sm ${theme} mobile `}>
      <PageHeader title="모임이름" url={`/club/main/${clubId}`} />
      <ChatList chatting={result.chatting} />
      <TextSendBar placeholder="채팅을 입력해주세요" />
    </div>
  )
}

export default ClubChatPage
