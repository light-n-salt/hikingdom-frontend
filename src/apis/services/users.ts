import apiRequest from 'apis/axios'
import { User } from 'types/user.interface'

// GET Request
export function checkNickname(nickname: string) {
  return apiRequest.get(`/members/auth/nickname-check/${nickname}`)
}

export function getProfile(nickname: string, size: number | null = null) {
  return apiRequest
    .get(`/members/${nickname}`, { params: { size } })
    .then((res) => res.data.result)
}

export function getUserInfo(setUserState: (userInfo: User) => void) {
  return apiRequest.get(`/members`).then((res) => setUserState(res.data.result))
}

export function getPastMeetups(
  nickname: string,
  hikingRecordId: number | null = null
) {
  return apiRequest.get(`/members/${nickname}/hiking`, {
    params: {
      hikingRecordId,
    },
  })
}

// POST Request
export function validEmail(email: string) {
  return apiRequest.post(`/members/auth/email-valid`, {
    email,
  })
}

export function signup(
  email: string,
  nickname: string,
  password: string,
  checkPassword: string
) {
  return apiRequest.post(`/members/auth/signup`, {
    email,
    nickname,
    password,
    checkPassword,
  })
}

export function login(email: string, password: string) {
  return apiRequest
    .post(`/members/auth/login`, {
      email,
      password,
    })
    .then((res) => {
      localStorage.setItem('accessToken', res.data.result.accessToken)
      localStorage.setItem('refreshToken', res.data.result.accessToken)
      // @ts-expect-error
      if (window.Token) {
        // @ts-expect-error
        window.Token.showToastMessage('Hello Native Callback')
      }
    })
}

export function logout() {
  return apiRequest.post(`/members/logout`).then(() => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('recoil-persist')
  })
}

// 신고
export function report(type: string, id: number) {
  return apiRequest.post(`reports`, { type, id })
}

// PUT request
export function findPw(email: string) {
  return apiRequest.put(`/members/auth/password-find`, {
    email,
  })
}

export function updateNickname(nickname: string) {
  return apiRequest.put(`/members/nickname-change`, {
    nickname,
  })
}

export function updatePw(
  password: string,
  newPassword: string,
  checkPassword: string
) {
  return apiRequest.put(`/members/password-change`, {
    password,
    newPassword,
    checkPassword,
  })
}

export function updateProfile(formData: FormData) {
  return apiRequest.put(`members/profile-image-change`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// DELETE Request
export function confirmEmail(email: string, authCode: string) {
  return apiRequest.delete(`/members/auth/email-valid`, {
    data: {
      email,
      authCode,
    },
  })
}
