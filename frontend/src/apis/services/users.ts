import apiRequest from 'apis/axios'
import { User } from 'types/user.interface'

// 닉네임 중복 체크
export function checkNickname(nickname: string) {
  return apiRequest.get(`/members/auth/nickname-check/${nickname}`)
}

// 유저 프로필 정보 조회
export function getProfile(nickname: string, size: number | null = null) {
  return apiRequest
    .get(`/members/${nickname}`, { params: { size } })
    .then((res) => res.data.result)
}

// 유저 정보 조회
export function getUserInfo(setUserState: (userInfo: User) => void) {
  return apiRequest.get(`/members`).then((res) => {
    setUserState(res.data.result)
    return res.data.result
  })
}

// 유저 일정 조회
export function getPastMeetups(
  nickname: string,
  hikingRecordId: number | null = null
) {
  return apiRequest
    .get(`/members/${nickname}/hiking`, {
      params: {
        hikingRecordId,
      },
    })
    .then((res) => res.data.result)
}

// 트래킹 정보 조회
export function getTrackingInfo(nickname: string, hikingRecordId: number) {
  return apiRequest
    .get(`/members/${nickname}/hiking/${hikingRecordId}`)
    .then((res) => res.data.result)
}

// 이메일 인증
export function validEmail(email: string) {
  return apiRequest.post(`/members/auth/email-valid`, {
    email,
  })
}

// 회원가입
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

// 로그인
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

// 로그아웃
export function logout() {
  return apiRequest.post(`/members/logout`).then(() => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('recoil-persist')
  })
}

// 신고 : ALBUM || REVIEW || MEMBER
export function report(type: 'ALBUM' | 'REVIEW' | 'MEMBER', id: number) {
  return apiRequest.post(`reports`, { type, id })
}

// 비밀번호 찾기
export function findPw(email: string) {
  return apiRequest.put(`/members/auth/password-find`, {
    email,
  })
}

// 닉네임 변경
export function updateNickname(nickname: string) {
  return apiRequest.put(`/members/nickname-change`, {
    nickname,
  })
}

// 비밀번호 변경
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

// 프로필 사진 변경
export function updateProfile(formData: FormData) {
  return apiRequest.put(`members/profile-image-change`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// 이메일 확인
export function confirmEmail(email: string, authCode: string) {
  return apiRequest.delete(`/members/auth/email-valid`, {
    data: {
      email,
      authCode,
    },
  })
}

// 회원탈퇴
export function signout() {
  return apiRequest.delete(`members/withdraw`)
}
