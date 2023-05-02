import apiRequest from 'apis/axios'

export function validEmail(email: string) {
  return apiRequest.post(`/members/auth/email-valid`, {
    email,
  })
}

export function confirmEmail(email: string, authCode: string) {
  return apiRequest.delete(`/members/auth/email-valid`, {
    data: {
      email,
      authCode,
    },
  })
}

export function checkNickname(nickname: string) {
  return apiRequest.get(`/members/auth/nickname-check/${nickname}`)
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
  return apiRequest.post(`/members/auth/login`, {
    email,
    password,
  })
}

export function findPw(email: string) {
  return apiRequest.put(`/members/auth/password-find`, {
    email,
  })
}
