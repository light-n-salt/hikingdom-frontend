import apiRequest from 'apis/AxiosInterceptor'
import { AxiosError, AxiosResponse } from 'axios'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import {
  MeetupMemberInfo,
  MeetupInfoDetail,
  MeetupReview,
} from 'types/meetup.interface'
import { ClubMember } from 'types/club.interface'
import toast from 'components/common/Toast'

// 일정 상세 조회
export function useMeetupDetailQuery(clubId: number, meetupId: number) {
  return useQuery<any, AxiosError, MeetupInfoDetail>(
    ['meetup', clubId, meetupId],
    () => apiRequest.get(`/clubs/${clubId}/meetups/${meetupId}/detail`),
    { select: (res) => res?.data.result }
  )
}

// 일정 멤버 조회
export function useMeetupMemberQuery(clubId: number, meetupId: number) {
  return useQuery<any, AxiosError, MeetupMemberInfo>(
    ['meetupMembers', clubId, meetupId],
    () => apiRequest.get(`/clubs/${clubId}/meetups/${meetupId}/members`),
    { select: (res) => res?.data.result, enabled: !!clubId }
  )
}

// 일정 멤버 상세 조회
export function useMembersDetailQuery(clubId: number, meetupId: number) {
  return useQuery<any, AxiosError, ClubMember[]>(
    ['meetupMembersDetail', clubId, meetupId],
    () => apiRequest.get(`/clubs/${clubId}/meetups/${meetupId}/members/detail`),
    { select: (res) => res?.data.result, enabled: !!clubId }
  )
}

// 일정 후기 조회
export function useMeetupReviewsQuery(clubId: number, meetupId: number) {
  return useQuery<any, AxiosError, MeetupReview[]>(
    ['meetupReviews', clubId, meetupId],
    () => apiRequest.get(`/clubs/${clubId}/meetups/${meetupId}/reviews`),
    { select: (res) => res?.data.result, enabled: !!clubId }
  )
}

// 일정 참여
export function useJoinMeetup(clubId: number, meetupId: number) {
  const queryClient = useQueryClient()
  return useMutation(
    () => apiRequest.post(`/clubs/${clubId}/meetups/${meetupId}/join`),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['meetupMembers', clubId, meetupId])
        queryClient.invalidateQueries(['meetup'])
        toast.addMessage('success', '일정에 참여했습니다')
      },
      onError: (err: AxiosResponse) => {
        toast.addMessage('error', err.data.message)
      },
    }
  )
}

// 일정 참여 취소
export function useUnJoinMeetup(clubId: number, meetupId: number) {
  const queryClient = useQueryClient()
  return useMutation(
    () => apiRequest.delete(`/clubs/${clubId}/meetups/${meetupId}/join`),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['meetupMembers', clubId, meetupId])
        queryClient.invalidateQueries(['meetup'])
        toast.addMessage('success', '일정 참여를 취소했습니다')
      },
      onError: (err: AxiosResponse) => {
        toast.addMessage('error', err.data.message)
      },
    }
  )
}

// 일정 사진 조회 : Todo
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

// 일정 사진 등록 : Todo
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

// 일정 후기 등록 : Todo
export function updateReview(
  clubId: number,
  meetupId: number,
  content: string
) {
  return apiRequest.post(`/clubs/${clubId}/meetups/${meetupId}/reviews`, {
    content,
  })
}

// 일정 삭제 : Todo
export function deleteMeetup(clubId: number, meetupId: number) {
  return apiRequest.delete(`/clubs/${clubId}/meetups/${meetupId}`)
}

// 일정 후기 삭제 : Todo
export function deleteReview(
  clubId: number,
  meetupId: number,
  reviewId: number
) {
  return apiRequest.delete(
    `/clubs/${clubId}/meetups/${meetupId}/reviews/${reviewId}`
  )
}
