// 소모임 채팅 내용 조회 - 채팅 내용
export interface Chat {
  chattingId: number
  memberId: number
  profileUrl: string
  nickname: string
  level: number
  content: string
  sendAt: string
  isContinue: boolean
}

// 소모임 채팅 내용 조회 - 채팅
export interface Chats {
  groupId: number
  groupName: string
  chatting: Chat[]
}
