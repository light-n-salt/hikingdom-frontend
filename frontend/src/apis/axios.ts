import axios, {
    AxiosError,
    AxiosInstance,
    AxiosRequestConfig,
    AxiosResponse,
} from 'axios'
import { useRecoilValue, useSetRecoilState } from 'recoil'
import { accessTokenState, refreshTokenState } from '../recoil/token/atoms'

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
        const accessToken = useRecoilValue(accessTokenState) // recoil에서 accessToken 읽어오기

        // JWT access 토큰이 있다면 Authorization 헤더에 추가
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

axiosInstance.interceptors.response.use(
    (response) => {
        return response
    },
    (error) => {
        const originalRequest = error.config

        // 만료된 JWT access 토큰이 감지되면 자동으로 새로운 JWT access 토큰 발급
        if (
            error.response?.status === 401 &&
            error.response?.data?.message === 'Token expired'
        ) {
            const refreshToken = useRecoilValue(refreshTokenState) // recoil에서 refreshToken 읽어오기

            axios
                .post(`${process.env.REACT_APP_API_BASE_URL}/refresh_token`, {
                    refreshToken,
                })
                .then((response) => {
                    const setAccessToken = useSetRecoilState(accessTokenState)

                    const newAccessToken = response.data.token
                    originalRequest.headers.Authorization = `Bearer ${newAccessToken}` // 새로운 JWT access 토큰으로 요청을 다시 보내기
                    setAccessToken(newAccessToken) // 새로운 access 토큰으로 recoil 업데이트
                    return axiosInstance(originalRequest)
                })
                .catch((error) => {
                    // JWT access 토큰 갱신에 실패한 경우, 로그인 페이지로 리다이렉트 하는 함수 추가
                    // 이 코드에서는 단순히 에러를 반환
                    return Promise.reject(error)
                })
        }

        return Promise.reject(error)
    }
)

export default axiosInstance
