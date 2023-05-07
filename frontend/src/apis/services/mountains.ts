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
