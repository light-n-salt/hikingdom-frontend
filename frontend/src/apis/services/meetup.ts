import apiRequest from 'apis/axios'
import { AxiosResponse } from 'axios'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import {
  MeetupMemberInfo,
  meetupInfoDetail,
  MeetupReview,
} from 'types/meetup.interface'
import { ClubMember } from 'types/club.interface'
import toast from 'components/common/Toast'

// 일정 상세 조회
export function useMeetupDetailQuery(clubId: number, meetupId: number) {
  const { isLoading, isError, data } = useQuery<meetupInfoDetail>(
    ['meetup', clubId, meetupId],
    () =>
      apiRequest
        .get(`/clubs/${clubId}/meetups/${meetupId}/detail`)
        .then((res) => res.data.result)
  )
  return { isLoading, isError, data }
}

// 일정 멤버 조회
export function useMeetupMemberQuery(clubId: number, meetupId: number) {
  const { isLoading, isError, data } = useQuery<MeetupMemberInfo>(
    ['meetupMembers', clubId, meetupId],
    () =>
      apiRequest
        .get(`/clubs/${clubId}/meetups/${meetupId}/members`)
        .then((res) => res.data.result)
  )
  return { isLoading, isError, data }
}

// 일정 멤버 상세 조회
export function useMembersDetailQuery(
  clubId: number | undefined,
  meetupId: number
) {
  const { isLoading, isError, data } = useQuery<ClubMember[]>(
    ['meetupMembersDetail', clubId, meetupId],
    () =>
      apiRequest
        .get(`/clubs/${clubId}/meetups/${meetupId}/members/detail`)
        .then((res) => res.data.result),
    { enabled: !!clubId }
  )
  return { isLoading, isError, data }
}

// 일정 후기 조회
export function useMeetupReviewsQuery(
  clubId: number | undefined,
  meetupId: number
) {
  const { isLoading, isError, data } = useQuery<MeetupReview[]>(
    ['meetupReviews', clubId, meetupId],
    () =>
      apiRequest
        .get(`/clubs/${clubId}/meetups/${meetupId}/reviews`)
        .then((res) => res.data.result),
    { enabled: !!clubId }
  )
  return { isLoading, isError, data }
}

// 일정 참여
export function useJoinMeetup(clubId: number, meetupId: number) {
  const queryClient = useQueryClient()
  const {
    isLoading,
    isError,
    mutate: joinMeetup,
  } = useMutation(
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
  return { isLoading, isError, joinMeetup }
}

// 일정 참여 취소
export function useUnJoinMeetup(clubId: number, meetupId: number) {
  const queryClient = useQueryClient()
  const {
    isLoading,
    isError,
    mutate: unJoinMeetup,
  } = useMutation(
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
  return { isLoading, isError, unJoinMeetup }
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
