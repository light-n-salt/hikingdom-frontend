import { Album } from './club.interface'

// 일정 전체 조회
export interface MeetupInfoList {
  startAt: number[]
}

//모임 일정 조회
export interface MeetupInfo {
  description: string
  meetupHostId: number
  meetupId: number
  meetupName: string
  mountainName: string
  startAt: string
  totalMember: number
}

// 일정 멤버
export interface MeetupMember {
  memberId: number
  profileUrl: string
}

// 일정 멤버 조회
export interface MeetupMemberInfo {
  totalMember: number
  isJoin: boolean
  memberInfo: MeetupMember[]
}

// 일정 후기 조회
export interface MeetupReview {
  memberId: number
  nickname: string
  profileUrl: string
  reviewId: number
  content: string
}

// 상세 일정 조회
export interface meetupInfoDetail extends MeetupInfo {
  join: boolean
  memberInfo: MeetupMember[]
  photoInfo: Album[]
  reviewInfo: MeetupReview[]
}

// 일정 생성
export interface Createmeetup {
  name: string
  mountainName: string
  startAt: string // 형식(YYYY.MM.DD HH:mm)
  description: string
}
