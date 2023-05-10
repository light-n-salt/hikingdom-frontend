import React from 'react'
import { getUserInfo } from 'apis/services/users'
import { useSetRecoilState } from 'recoil'
import { userInfoState } from 'recoil/atoms'
import { useQuery } from '@tanstack/react-query'
import { User } from 'types/user.interface'

function useUserQuery() {
  const setUserInfo = useSetRecoilState(userInfoState)

  return useQuery<User>(['user'], () => getUserInfo(setUserInfo), {
    cacheTime: Infinity,
    staleTime: Infinity,
  })
}

export default useUserQuery
