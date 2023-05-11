import apiRequest from 'apis/axios'

// GET Request
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
