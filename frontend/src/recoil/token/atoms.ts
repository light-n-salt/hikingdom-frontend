import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

// JWT 토큰 관련 설정
const ACCESS_TOKEN_KEY = 'AccessToken'
const REFRESH_TOKEN_KEY = 'RefreshToken'

// Recoil atom 정의
const accessTokenState = atom<string>({
    key: ACCESS_TOKEN_KEY,
    default: localStorage.getItem(ACCESS_TOKEN_KEY) ?? '',
    effects_UNSTABLE: [persistAtom],
})

const refreshTokenState = atom<string>({
    key: REFRESH_TOKEN_KEY,
    default: localStorage.getItem(REFRESH_TOKEN_KEY) ?? '',
    effects_UNSTABLE: [persistAtom],
})

export { accessTokenState, refreshTokenState }
