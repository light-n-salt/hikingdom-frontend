import { getUserInfo } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'
import { UserInfo } from 'types/user.interface'

function useUserQuery() {
  return useQuery<UserInfo>(['user'], () => getUserInfo())
}

export default useUserQuery
