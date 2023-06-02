import apiRequest from 'apis/AxiosInterceptor'

// 일정 상세 조회
export function getMeetupDetail(clubId: number, meetupId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/meetups/${meetupId}/detail`)
    .then((res) => res.data.result)
}

// 일정 멤버 조회
export function getMeetupMembers(clubId: number, meetupId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/meetups/${meetupId}/members`)
    .then((res) => res.data.result)
}

// 일정 멤버 상세 조회
export function getMembersDetail(clubId: number, meetupId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/meetups/${meetupId}/members/detail`)
    .then((res) => res.data.result)
}

// 일정 사진 조회
export function getMeetupAlbum(
  clubId: number,
  meetupId: number,
  photoId: number | null = null,
  size: number | null = null
) {
  return apiRequest
    .get(`/clubs/${clubId}/meetups/${meetupId}/photos`, {
      params: {
        photoId,
        size,
      },
    })
    .then((res) => res.data.result)
}

// 일정 후기 조회
export function getReviews(clubId: number, meetupId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/meetups/${meetupId}/reviews`)
    .then((res) => res.data.result)
}

// 일정 사진 등록
export function updateMeetupAlbum(
  clubId: number,
  meetupId: number,
  formData: FormData
) {
  return apiRequest.post(
    `/clubs/${clubId}/meetups/${meetupId}/photos`,
    formData,
    {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    }
  )
}

// 일정 후기 등록
export function updateReview(
  clubtId: number,
  meetupId: number,
  content: string
) {
  return apiRequest.post(`/clubs/${clubtId}/meetups/${meetupId}/reviews`, {
    content,
  })
}

// 일정 참여
export function updateJoin(clubId: number, meetupId: number) {
  return apiRequest.post(`/clubs/${clubId}/meetups/${meetupId}/join`)
}

// 일정 삭제
export function deleteMeetup(clubId: number, meetupId: number) {
  return apiRequest.delete(`/clubs/${clubId}/meetups/${meetupId}`)
}

// 일정 후기 삭제
export function deleteReview(
  clubId: number,
  meetupId: number,
  reviewId: number
) {
  return apiRequest.delete(
    `/clubs/${clubId}/meetups/${meetupId}/reviews/${reviewId}`
  )
}

// 일정 참여 취소
export function deleteJoin(clubId: number, meetupId: number) {
  return apiRequest.delete(`/clubs/${clubId}/meetups/${meetupId}/join`)
}
