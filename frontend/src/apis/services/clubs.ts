import apiRequest from 'apis/axios'

// 소모임 정보 조회
export function clubSimpleInfo(clubId: number) {
  return apiRequest.get(`/clubs/${clubId}`)
}

export function getRanking(sort = '', clubId: number | null = null) {
  return apiRequest.get(`/clubs/ranking`, {
    params: {
      sort,
      clubId,
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
