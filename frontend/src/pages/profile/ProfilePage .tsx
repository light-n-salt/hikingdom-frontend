import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import IconButton from 'components/common/IconButton'
import bell from 'assets/images/bell.png'

function ProfilePage() {
    const { theme } = useContext(ThemeContext)
    const navigate = useNavigate()

    const userRecord = {
        totalHikingCount: 10,
        totalMountainCount: 30,
        totalDuration: '30:23',
        totalDistance: 34,
        totalAlt: 34,
    }
    return (
        <div className={`page ${theme} mobile ${styles['profile-page']}`}>
            <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
            <div>
                <UserProfile
                    imgUrl="https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80"
                    nickname="이병호리병"
                    email="조혜진진자라@gamil.com"
                    userRecord={userRecord}
                />
            </div>
            <div>
                <span>등산기록</span>
            </div>
        </div>
    )
}

export default ProfilePage
