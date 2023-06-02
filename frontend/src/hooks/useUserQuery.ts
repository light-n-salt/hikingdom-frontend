import { UserInfo } from 'types/user.interface'

import { useQuery } from '@tanstack/react-query'

import { getUserInfo } from 'apis/services/users'

function useUserQuery() {
  return useQuery<UserInfo>(['user'], () => getUserInfo())
}

export default useUserQuery
