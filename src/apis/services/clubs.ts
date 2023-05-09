import apiRequest from 'apis/axios'

// 소모임 정보 조회
export function getClubSimpleInfo(clubId: number) {
  return apiRequest.get(`clubs/${clubId}`)
}

// 소모임 랭킹 조회
export function getRanking(
  sort = '',
  clubId: number | null = null,
  size: number | null = null
) {
  return apiRequest.get(`/clubs/ranking`, {
    params: {
      sort,
      clubId,
      size,
    },
  })
}

// 소모임 조회
export function getClubs(query = '', word = '', clubId: number | null = null) {
  return apiRequest.get(`/clubs`, {
    params: {
      query,
      word,
      clubId,
    },
  })
}

// 소모임 이름 중복 확인
export function checkClubName(name: string) {
  return apiRequest.get(`/clubs/check-duplicate/${name}`)
}

// 주소 조회
export function getLocationCode(
  query: 'sido' | 'gugun',
  word: string | null = null
) {
  return apiRequest.get(`/info/location`, {
    params: {
      query,
      word,
    },
  })
}

// 소모임 월별 일정 조회
export function getMonthMeetups(clubId: string, month: string) {
  return apiRequest.get(`/clubs/${clubId}/meetups/month/${month}`)
}

// 소모임 일별 일정 조회
export function getDateMeetups(clubId: string, date: string) {
  return apiRequest.get(`/clubs/${clubId}/meetups/date/${date}`)
}

// 소모임 앨범 조회
export function getClubAlbum(
  clubId: number,
  size: number | null = null,
  photoId: number | null = null
) {
  return apiRequest.get(`/clubs/${clubId}/photos`, {
    params: {
      photoId,
      size,
    },
  })
}

// 소모임 정보 조회
export function getClubInfo(clubId: number) {
  return apiRequest.get(`/clubs/${clubId}/detail`)
}

// 소모임 생성
export function createClub(
  name: string,
  description: string,
  dongCode: string
) {
  return apiRequest.post(`/clubs`, {
    name,
    description,
    dongCode,
  })
}

// 일정 생성
export function createMeetup(
  clubId: number | string,
  name: string,
  mountainId: number | string,
  startAt: string,
  description: string
) {
  return apiRequest.post(`/clubs/${clubId}/meetups`, {
    name,
    mountainId,
    startAt,
    description,
  })
}

// 소모임 앨범 사진 삭제
export function deleteAlbum(clubId: number, photoId: number) {
  return apiRequest.delete(`clubs/${clubId}/photos/${photoId}`)
}
