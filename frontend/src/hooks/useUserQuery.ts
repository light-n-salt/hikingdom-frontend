import { getUserInfo } from 'apis/services/users'
import { useQuery } from '@tanstack/react-query'
import { User } from 'types/user.interface'

function useUserQuery() {
  return useQuery<User>(['user'], () => getUserInfo())
}

export default useUserQuery
