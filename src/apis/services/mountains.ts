import apiRequest from 'apis/axios'

// GET Request
export function getMountains(word: string, mountainId?: number) {
  const params = {
    word,
    mountainId,
  }
  return apiRequest.get(`info/mountains`, {
    params,
  })
}
