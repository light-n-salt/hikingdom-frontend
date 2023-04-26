// 소모임 랭킹 조회
// 소모임 검색
// 소모임 가입 신청 조회
export interface GroupInfo {
    groupId: number
    groupName: string
    location: string
    totalMember: number
    totalDuration: number
    totalDistance: number
    participationRate: number
    ranking: number
}

// 소모임 정보 조회
export interface GroupInfo {
    hostId: number
    groupId: number
    groupName: string
}

// 에셋 정보
// 소모임 산 조회
// 오늘의 모임 산 (메인페이지)
export interface AssetInfo {
    mountainName: string | undefined
    scheduleId: number | undefined
    assetUrl: string
    row: number
    column: number
}

// 소모임 상세 조회
export interface GroupInfoDetail {
    participationRate: string
    totalDuration: string
    totalDistance: number
    totalAlt: number
    description: string
    asset: AssetInfo[]
}

// 소모임 멤버
// 일정 멤버 상세 조회
export interface GroupMember {
    memberId: number
    nickname: string
    profileUrl: string
    level: number
    totalHikingCount: number
    totalDuration: number
    totalDistance: number
}

// 소모임 멤버 리스트 조회
export interface GroupMemberList {
    request: GroupMember[] | undefined
    member: GroupMember[]
}

// 소모임 사진 앨범 조회
// 일정 사진 조회
export interface Album {
    photoId: number
    memberId: number
    imgUrl: string
    createdAt: string
}

// 지역 코드 조회
export interface SearchCode {
    dongCode: string
    sidoName: string
    sigunguName: string
}

// 소모임 생성
export interface Creategroup {
    name: string
    description: string
    dongCode: number
}
