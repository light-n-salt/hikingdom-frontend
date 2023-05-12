// 소모임 채팅 내용 조회 - 채팅 내용
export interface Chat {
  chatId: string
  memberId: number
  content: string
  sendAt: string
}

export interface ChatMember {
  memberId: number
  nickname: string
  profileUrl: string
  level: number
}

// 소모임 채팅 내용 조회 - 채팅
export interface Chats {
  status: string
  members: ChatMember[]
  chats: Chat[]
}
