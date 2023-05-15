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
import { NavLink } from 'react-router-dom'
import Image from 'components/common/Image'

function NavigationBar() {
  const navigate = useNavigate()

  const setUserInfo = useSetRecoilState(userInfoState)

  const {
    data: userInfo,
    isLoading,
    isError,
  } = useQuery<User>(['user'], () => getUserInfo(setUserInfo), {
    cacheTime: Infinity,
    staleTime: Infinity,
  })

  return (
    <>
      {isError || isLoading ? (
        <Loading />
      ) : (
        <nav className={styles.nav}>
          <NavLink
            to="/main"
            className={({ isActive }) =>
              isActive ? styles.active : styles.inactive
            }
          >
            <IoHomeOutline />
          </NavLink>
          <NavLink
            to="/rank"
            className={({ isActive }) =>
              isActive ? styles.active : styles.inactive
            }
          >
            {' '}
            <IoPodiumOutline />
          </NavLink>
          <NavLink
            to={
              userInfo?.clubId ? `/club/${userInfo.clubId}/main` : '/club/none'
            }
            className={({ isActive }) =>
              isActive ? styles.active : styles.inactive
            }
          >
            <IoPeopleOutline />
          </NavLink>
          <NavLink
            to={`/profile/${userInfo.nickname}`}
            className={({ isActive }) =>
              isActive ? styles.active : styles.inactive
            }
          >
            <img src={userInfo.profileUrl} />
          </NavLink>
        </nav>
      )}
    </>
  )
}

export default NavigationBar

// <div className={styles.nav}>
//   <IconButton
//     icon={<IoPodiumOutline />}
//     color="gray"
//     onClick={() => navigate('/rank')}
//   />
//   <IconButton
//     icon={<IoHomeOutline />}
//     onClick={() => navigate('/main')}
//   />
//   <IconButton icon={<IoPeopleOutline />} onClick={onClickToClub} />
//   <IconButton
//     imgSrc={userInfo.profileUrl}
//     onClick={() => navigate(`/profile/${userInfo.nickname}`)}
//   />
// </div>
