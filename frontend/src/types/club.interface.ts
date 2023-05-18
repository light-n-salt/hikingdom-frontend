// 소모임 정보 조회
export interface ClubSimpleInfo {
  hostId: number
  clubId: number
  clubName: string
}

// 소모임 랭킹 조회
// 소모임 검색
// 소모임 가입 신청 목록 조회
export interface ClubInfo {
  clubId: number
  clubName: string
  location: string
  totalMember: number
  totalMountainCount: number
  totalMeetupCount: number
  totalAssetCount: number
  ranking: number
}

// 에셋 정보
// 소모임 산 조회
export interface AssetInfo {
  mountainName?: string | undefined
  meetupId?: number | undefined
  assetUrl: string
  row?: number
  column?: number
}

// 오늘의 모임 산
export interface TodayClubMt {
  clubId: number
  assets: AssetInfo[]
}

// 소모임 상세 조회
export interface ClubDetailInfo {
  clubName: string
  isJoin: boolean
  totalMember: number
  totalMountainCount: number
  totalMeetupCount: number
  totalAssetCount: number
  description: string
  assets: AssetInfo[]
}

// 소모임 멤버
// 일정 멤버 상세 조회
export interface ClubMember {
  memberId: number
  nickname: string
  profileUrl: string
  level: number
  totalHikingCount: number
  totalDuration: number
  totalDistance: number
}

// 소모임 멤버 리스트 조회
export interface ClubMemberList {
  request?: ClubMember[] | undefined
  member: ClubMember[]
}

// 소모임 사진 앨범 조회
// 일정 사진 조회
export interface Album {
  photoId: number
  imgUrl: string
  isOwner: boolean
  createdAt?: string
}

// 지역 코드 조회
export interface SearchCode {
  dongCode: string
  sidoName: string
  sigunguName: string
}

// 소모임 생성
export interface CreateClub {
  name: string
  description: string
  dongCode: number
}
