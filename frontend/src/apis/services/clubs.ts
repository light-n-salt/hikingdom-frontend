import apiRequest from 'apis/axios'

// 소모임 정보 조회
export function getClubSimpleInfo(clubId: number) {
  return apiRequest.get(`clubs/${clubId}`)
}

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

export function getClubs(query = '', word = '', clubId: number | null = null) {
  return apiRequest.get(`/clubs`, {
    params: {
      query,
      word,
      clubId,
    },
  })
}

export function checkClubName(name: string) {
  return apiRequest.get(`/clubs/check-duplicate/${name}`)
}

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

export function getMonthMeetups(clubId: string, month: string) {
  return apiRequest.get(`/clubs/${clubId}/meetups/month/${month}`)
}

export function getDateMeetups(clubId: string, date: string) {
  return apiRequest.get(`/clubs/${clubId}/meetups/date/${date}`)
}

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

export function getClubInfo(clubId: number) {
  return apiRequest.get(`/clubs/${clubId}/detail`)
}
