import { Album } from './group.interface'

// 일정 전체 조회
export interface SchedInfoList {
    startAt: number[]
}

//모임 일정 조회
export interface SchedInfo {
    scheduleHostId: number
    scheduleId: number
    scheduleName: string
    mountainName: string
    startDate: string
    startTime: string
    description: string
    totalMmember: number
}

// 일정 멤버
export interface SchedMember {
    memberId: number
    profileUrl: string
}

// 일정 멤버 조회
export interface SchedMemberInfo {
    totalMmember: number
    isJoin: boolean
    memberInfo: SchedMember[]
}

// 일정 후기 조회
export interface SchedReview {
    memberId: number
    nickname: string
    profileUrl: string
    reviewId: number
    content: string
}

// 상세 일정 조회
export interface SchedInfoDetail extends SchedInfo {
    isJoin: boolean
    memberInfo: SchedMember[]
    photoInfo: Album[]
    reviewInfo: SchedReview[]
}

// 일정 생성
export interface CreateSched {
    name: string
    mountainName: string
    startDate: string
    startTime: string
    description: string
}
