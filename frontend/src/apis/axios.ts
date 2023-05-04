import axios, {
  AxiosError,
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
} from 'axios'

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL, // process.env에서 baseURL 읽어오기
  headers: {
    'Content-Type': 'application/json',
  },
})

// Axios 인스턴스에 Interceptor 추가
axiosInstance.interceptors.request.use(
  (config) => {
    // 인증관련 요청이 아닐 경우에만
    if (config.url?.slice(0, 13) !== '/members/auth') {
      const accessToken = localStorage.getItem('accessToken') // recoil에서 accessToken 읽어오기

      // JWT access 토큰이 있다면 Authorization 헤더에 추가
      if (accessToken) {
        config.headers.Authorization = accessToken
      }
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

axiosInstance.interceptors.response.use(
  (response) => {
    console.log(response)
    return response
  },
  (error) => {
    console.log(error)
    const originalRequest = error.config

    // 만료된 JWT access 토큰이 감지되면 자동으로 새로운 JWT access 토큰 발급
    if (
      error.response?.status === 401 &&
      error.response?.data?.message === 'Token expired'
    ) {
      const refreshToken = localStorage.getItem('refreshToken') // recoil에서 refreshToken 읽어오기

      axios
        .post(`${process.env.REACT_APP_API_BASE_URL}/refresh_token`, {
          refreshToken,
        })
        .then((response) => {
          const newAccessToken = response.data.result.accessToken
          const newRefreshToken = response.data.result.refreshToken
          localStorage.setItem('accessToken', newAccessToken)
          localStorage.setItem('refreshToken', newRefreshToken)
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}` // 새로운 JWT access 토큰으로 요청을 다시 보내기
          return axiosInstance(originalRequest)
        })
        .catch((error) => {
          // JWT access 토큰 갱신에 실패한 경우, 로그인 페이지로 리다이렉트 하는 함수 추가
          // 이 코드에서는 단순히 에러를 반환
          return Promise.reject(error)
        })
    }

    return Promise.reject(error.response)
  }
)

export default axiosInstance