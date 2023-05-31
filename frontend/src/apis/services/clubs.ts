import apiRequest from 'apis/AxiosInterceptor'
import { untilMidnight } from 'utils/untilMidnight'
import { useQuery, useInfiniteQuery, useMutation } from '@tanstack/react-query'
import { ClubSimpleInfo, InfiniteClubInfo } from 'types/club.interface'
import toast from 'components/common/Toast'
import { AxiosResponse } from 'axios'

// 소모임 정보 조회
export function useClubSimpleInfoQuery(clubId: number) {
  const { isLoading, isError, data, isSuccess } = useQuery<ClubSimpleInfo>(
    ['user', 'clubInfo'],
    () => apiRequest.get(`clubs/${clubId}`).then((res) => res.data.result),
    {
      cacheTime: untilMidnight(),
      staleTime: untilMidnight(),
      enabled: !!clubId,
    }
  )
  return {
    isLoading,
    isError,
    data,
    isSuccess,
  }
}

// 소모임 랭킹 조회
const getRanking = async (
  sort = '',
  clubId: number | null = null,
  size: number | null = null
) => {
  return await apiRequest
    .get(`/clubs/ranking`, {
      params: {
        sort,
        clubId,
        size,
      },
    })
    .then((res) => res.data.result)
}

export function useclubRankTop3Query() {
  const {
    isLoading: isClubRankTop3Loading,
    isError: isClubRankTop3Error,
    data: clubRankTop3,
    isSuccess,
  } = useQuery<InfiniteClubInfo>(['clubRankTop3'], () =>
    getRanking('', null, 3)
  )
  return {
    isClubRankTop3Loading,
    isClubRankTop3Error,
    clubRankTop3,
    isSuccess,
  }
}

export function useInfiniteClubInfoQuery(filter: string) {
  const {
    isLoading,
    isError,
    data,
    isSuccess,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery<InfiniteClubInfo>({
    queryKey: ['rank', filter],
    queryFn: ({ pageParam = null }) => getRanking(filter, pageParam),
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext ? lastPage.content.slice(-1)[0].clubId : undefined
    },
    cacheTime: untilMidnight(),
    staleTime: untilMidnight(),
  })
  return {
    isLoading,
    isError,
    data,
    isSuccess,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
  }
}

// 소모임 조회  >>>  SearchClubPage
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
export function useCheckClubNameQuery(name: string, isClicked: boolean) {
  const { isLoading, isError, data, isSuccess } = useQuery(
    ['duplicate', name],
    () =>
      apiRequest
        .get(`/clubs/check-duplicate/${name}`)
        .then((res) => res.data.result),
    {
      onSuccess: () => {
        toast.addMessage('success', '중복확인 되었습니다')
      },
      onError: (err: AxiosResponse) => {
        toast.addMessage('error', err.data.message)
      },
      cacheTime: 0,
      staleTime: 0,
      enabled: !!isClicked,
    }
  )
  return {
    isLoading,
    isError,
    data,
    isSuccess,
  }
}

// 주소 조회
// export function getLocationCode(
//   query: 'sido' | 'gugun',
//   word: string | null = null
// ) {
//   return apiRequest.get(`/info/location`, {
//     params: {
//       query,
//       word,
//     },
//   })
// }

export function useGetLocationCodeQuery(
  query: 'sido' | 'gugun',
  word: string | null = null
) {
  const { isLoading, isError, data, isSuccess } = useQuery(
    ['checkLocation', query, word],
    () =>
      apiRequest
        .get(`/info/location`, { params: { query, word } })
        .then((res) => res.data.result)
  )
  return {
    isLoading,
    isError,
    data,
    isSuccess,
  }
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

// 소모임 채팅 조회
export function getChats(
  clubId: number,
  chatId: string | null = null,
  size: number | null = 50
) {
  return apiRequest
    .get(`/clubs/${clubId}/chats`, {
      baseURL: 'https://hikingdom.kr/chat',
      params: { chatId, size },
    })
    .then((res) => res.data.result)
}

// 소모임 채팅 멤버 조회
export function getMembers(clubId: number) {
  return apiRequest
    .get(`/clubs/${clubId}/members`, { baseURL: 'https://hikingdom.kr/chat' })
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
  return apiRequest.delete(`/clubs/${clubId}/photos/${photoId}`)
}

export function postJoinClub(clubId: number) {
  return apiRequest.post(`/clubs/${clubId}/join-request`)
}

// 소모임 가입 신청 목록 조회
export function getClubRequest() {
  return apiRequest
    .get(`/members/clubs/my-requests`)
    .then((res) => res.data.result)
}

// 소모임 가입 신청 취소
export function deleteClubRequest(clubId: number) {
  return apiRequest.delete(`/clubs/${clubId}/join-request`)
}

// 소모임 멤버 조회
export function getClubMember(clubId: number) {
  return apiRequest.get(`/clubs/${clubId}/members`)
}

// 소모임 탈퇴
export function deleteClub(clubId: number) {
  return apiRequest.delete(`/clubs/${clubId}/members`)
}

// 소모임 가입 신청
export function updateClubMember(clubId: number, memberId: number) {
  return apiRequest.post(`/clubs/${clubId}/admin/requests/${memberId}`)
}

// 소모임 가입 신청취소
export function deleteClubMember(clubId: number, memberId: number) {
  return apiRequest.delete(`/clubs/${clubId}/admin/requests/${memberId}`)
}

// 오늘의 소모임 산 조회 (Main)
export function getTodayClubMt() {
  return apiRequest.get(`/info/today/club`).then((res) => res.data.result)
}
