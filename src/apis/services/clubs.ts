import apiRequest from 'apis/axios'

// 소모임 정보 조회
export function getClubSimpleInfo(clubId: number) {
  return apiRequest.get(`clubs/${clubId}`).then((res) => res.data.result)
}

// 소모임 랭킹 조회
export function getRanking(
  sort = '',
  clubId: number | null = null,
  size: number | null = null
) {
  return apiRequest
    .get(`/clubs/ranking`, {
      params: {
        sort,
        clubId,
        size,
      },
    })
    .then((res) => res.data.result)
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
  photoId: number | null = null,
  size: number | null = null
) {
  return apiRequest
    .get(`/clubs/${clubId}/photos`, {
      params: {
        photoId,
        size,
      },
    })
    .then((res) => res.data.result)
}

// 소모임 정보 조회
export function getClubInfo(clubId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/detail`)
    .then((res) => res.data.result)
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

export function postJoinClub(clubId: number) {
  return apiRequest.post(`/clubs/${clubId}/join-request`)
}

// 소모임 가입 신청 목록 조회
export function getClubRequest() {
  return apiRequest.get(`members/clubs/my-requests`)
}

// 소모임 가입 신청 취소
export function deleteClubRequest(clubId: number) {
  return apiRequest.delete(`clubs/${clubId}/join-request`)
}

export function getClubMember(clubId: number) {
  return apiRequest.get(`clubs/${clubId}/members`)
}

export function deleteClub(clubId: number) {
  return apiRequest.delete(`clubs/${clubId}/members`)
}

export function updateClubMember(clubId: number, memberId: number) {
  return apiRequest.post(`clubs/${clubId}/admin/requests/${memberId}`)
}

export function deleteClubMember(clubId: number, memberId: number) {
  return apiRequest.delete(`clubs/${clubId}/admin/requests/${memberId}`)
}
