// 개인정보 조회
//회원 프로필 조회 - 회원 기본 정보
export interface User {
    email: string
    nickname: string
    profileUrl: string
    grouldId: number | undefined
    level: number | undefined
}

// 회원 프로필 조회 - 회원 등산 기록
export interface UserRecord {
    totalHikingCount: number
    totalMountainCount: number
    totalDuration: string
    totalDistance: number
    totalAlt: number
}

//  등산 전체 기록 조회
// 회원 프로필 조회 - 등산 기록
export interface UserHiking {
    hikingRecordId: number
    mountainName: string
    startAt: string
    totalDuration: string
    totalDistance: number
    maxAlt: number
    isGroup: boolean
    scheduleId: number
    scheduleName: string
}

// 회원 프로필 조회
export interface UserProfile extends User, UserRecord {
    hikingRecords: UserHiking[]
}

// 등산 기록 상세 조회
export interface UserHikingDetail {
    gpsRoute: string
    totalDistance: number
    maxAlt: number
    totalDuration: string
}

// 전체 알림 조회
export interface UserAlarm {
    alarmId: number
    title: string
    content: string
    createdAt: string
    isRead: boolean
}
