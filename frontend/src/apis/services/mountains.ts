import apiRequest from 'apis/AxiosInterceptor'
import { useInfiniteQuery, useQuery } from '@tanstack/react-query'
import { AxiosError } from 'axios'
import { MtInfo, MtInfoDetail } from 'types/mt.interface'
import { untilMidnight } from 'utils/untilMidnight'

// 산 조회
export function useInfiniteMountainsQuery(word: string, query = 'name') {
  return useInfiniteQuery<any, AxiosError>({
    queryKey: ['mountains', { query, word }],
    queryFn: ({ pageParam = null }) =>
      apiRequest
        .get(`/info/mountains`, {
          params: {
            word,
            mountainId: pageParam,
            query,
          },
        })
        .then((res) => res.data.result),
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext
        ? lastPage.content.slice(-1)[0].mountainId
        : undefined
    },
  })
}

// 오늘의 산 조회
export function useTodayMountainsQuery() {
  return useQuery<any, AxiosError, MtInfo[]>(
    ['todayMountains'],
    () => apiRequest.get(`/info/today/mountain`),
    {
      select: (res) => res.data.result,
      cacheTime: untilMidnight(),
      staleTime: untilMidnight(),
    }
  )
}

// 산 상세 정보 조회
export function useMountainInfoQuery(mountainId: number) {
  return useQuery<any, AxiosError, MtInfoDetail>(
    ['mountainInfo', { mountainId }],
    () => apiRequest.get(`/info/mountains/${mountainId}`),
    {
      select: (res) => res.data.result,
      cacheTime: untilMidnight(),
      staleTime: untilMidnight(),
    }
  )
}
