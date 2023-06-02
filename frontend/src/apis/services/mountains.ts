import apiRequest from 'apis/AxiosInterceptor'
import { useQuery } from '@tanstack/react-query'
import { AxiosError } from 'axios'
import { MtInfo } from 'types/mt.interface'

export function getMountains(
  word: string,
  mountainId: number | null = null,
  query = 'name'
) {
  const params = {
    word,
    mountainId,
    query,
  }
  return apiRequest.get(`/info/mountains`, {
    params,
  })
}

// 산 조회
export function useMountainsQuery(
  word: string,
  mountainId: number | null = null,
  query = 'name'
) {
  const params = {
    word,
    mountainId,
    query,
  }
  return useQuery<any, AxiosError, MtInfo[]>(
    ['mountains'],
    () =>
      apiRequest.get(`/info/mountains`, {
        params,
      }),
    { select: (res) => res?.data.result }
  )
}

export function getTodayMountains() {
  return apiRequest
    .get(`/info/today/mountain`)
    .then((res) => {
      return res.data.result
    })
    .catch(() => {
      return []
    })
}

export function getMountainInfo(mountainId: number) {
  return apiRequest
    .get(`/info/mountains/${mountainId}`)
    .then((res) => res.data.result)
}
