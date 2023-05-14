// 개인정보 조회
//회원 프로필 조회 - 회원 기본 정보
export interface User {
  email: string
  nickname: string
  profileUrl: string
  memberId?: number
  clubId?: number | undefined
  level?: number | undefined
}

// 회원 프로필 조회 - 회원 등산 기록
export interface UserRecord {
  totalHikingCount: number
  totalMountainCount: number
  totalDuration: number
  totalDistance: number
  totalAlt: number
}

//  등산 전체 기록 조회
// 회원 프로필 조회 - 등산 기록
export interface UserHiking {
  hikingRecordId: number
  mountainName: string
  startAt: string
  totalDuration: number
  totalDistance: number
  maxAlt: number
  isSummit: boolean
  isMeetup: boolean
  meetupId: number
  meetupName: string
}

// 회원 프로필 조회
export interface UserProfileInfo extends User, UserRecord {
  hikingRecords: UserHiking[]
}

export interface RecordInfo {
  alt: number
  lat: number
  lng: number
}

export interface GpsRoute {
  gpsRoute: RecordInfo[]
}

// 등산 기록 상세 조회
export interface UserHikingDetail {
  gpsRoute: GpsRoute
  mountainName: string
  startAt: string
  totalDistance: number
  maxAlt: number
  totalDuration: number
}

// 전체 알림 조회
export interface UserAlarm {
  notificationId: number
  title: string
  body: string
  sendAt: string
  isRead: boolean
}
