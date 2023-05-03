import apiRequest from 'apis/axios'

// 소모임 정보 조회
export function clubSimpleInfo(club_Id: number) {
  return apiRequest.get(`/clubs/${club_Id}`)
}
