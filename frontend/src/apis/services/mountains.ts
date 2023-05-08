import apiRequest from 'apis/axios'

// GET Request
export function getMountains(word: string, mountainId: number | null = null) {
  const params = {
    word,
    mountainId,
  }
  return apiRequest.get(`info/mountains`, {
    params,
  })
}

export function getTodayMountains() {
  return apiRequest.get(`info/today/mountain`).then((res) => res.data.result)
}

export function getMountainInfo(mountainId: number) {
  return apiRequest
    .get(`info/mountains/${mountainId}`)
    .then((res) => res.data.result)
}
