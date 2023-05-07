import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'
import { User } from 'types/user.interface'

const { persistAtom } = recoilPersist()

const userInfoState = atom<User>({
  key: 'UserInfo',
  default: JSON.parse(localStorage.getItem('UserInfo') || '{}'),
  effects_UNSTABLE: [persistAtom],
})

export { userInfoState }
