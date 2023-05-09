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

  function onClickToClub() {
    if (userInfo?.clubId) {
      navigate(`/club/${userInfo.clubId}/main`)
    } else {
      navigate(`/club/none`)
    }
  }

  return (
    <>
      {isError || isLoading ? (
        <Loading />
      ) : (
        <div className={styles.nav}>
          <IconButton
            icon={<IoPodiumOutline />}
            onClick={() => navigate('/rank')}
          />
          <IconButton
            icon={<IoHomeOutline />}
            onClick={() => navigate('/main')}
          />
          <IconButton icon={<IoPeopleOutline />} onClick={onClickToClub} />
          <IconButton
            imgSrc={userInfo.profileUrl}
            onClick={() => navigate(`/profile/${userInfo.nickname}`)}
          />
        </div>
      )}
    </>
  )
}

export default NavigationBar
