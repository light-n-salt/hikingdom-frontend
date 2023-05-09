import React from 'react'
import styles from './NavigationBar.module.scss'
import IconButton from './IconButton'
import {
  IoPeopleOutline,
  IoHomeOutline,
  IoPodiumOutline,
} from 'react-icons/io5'
import { getUserInfo } from 'apis/services/users'
import { useSetRecoilState } from 'recoil'
import { userInfoState } from 'recoil/atoms'
import { User } from 'types/user.interface'
import { useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'
import Loading from './Loading'

function NavigationBar() {
  const navigate = useNavigate()

  const setUserInfo = useSetRecoilState(userInfoState)

  const {
    data: userInfo,
    isLoading,
    isError,
  } = useQuery<User>(['userHiking'], () => getUserInfo(setUserInfo))

  return (
    <>
      {isError || isLoading ? (
        <Loading />
      ) : (
        <div className={styles.nav}>
          {/* <IconButton icon={<IoPeopleOutline />} />
        <IconButton icon={<IoPodiumOutline />} />
        <IconButton icon={<IoHomeOutline />} />
        <IconButton imgSrc= {userInfo.profileUrl}/>} /> */}
        </div>
      )}
    </>
  )
}

export default NavigationBar
