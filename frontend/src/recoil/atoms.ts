import { atom } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist({
  key: 'recoil-persist', // this key is using to store data in local storage
  storage: localStorage, // configurate which stroage will be used to store the data
})

const themeState = atom<'light' | 'dark'>({
  key: 'Theme',
  default: 'light',
  effects_UNSTABLE: [persistAtom],
})

export { themeState }
