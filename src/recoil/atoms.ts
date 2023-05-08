import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'
import { User } from 'types/user.interface'
import { ClubSimpleInfo } from 'types/club.interface'

const { persistAtom } = recoilPersist()

const userInfoState = atom<User>({
  key: 'UserInfo',
  default: JSON.parse(localStorage.getItem('UserInfo') || '{}'),
  effects_UNSTABLE: [persistAtom],
})

const clubInfoState = atom<ClubSimpleInfo>({
  key: 'ClubInfo',
  default: {
    hostId: 0,
    clubId: 0,
    clubName: '',
  },
})

export { userInfoState, clubInfoState }
