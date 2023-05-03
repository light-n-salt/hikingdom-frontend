import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfilePage.module.scss'
import { useNavigate } from 'react-router-dom'

import UserProfile from 'components/user/UserProfile'
import PastMeetupList from 'components/user/PastMeetupList'
import IconButton from 'components/common/IconButton'

import bell from 'assets/images/bell.png'

function ProfilePage() {
    const { theme } = useContext(ThemeContext)
    const navigate = useNavigate()

    return (
        <div className={`page p-sm ${theme} ${styles.profile}`}>
            <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
            <UserProfile />
            <div className={styles.title}>등산기록</div>
            <PastMeetupList />
        </div>
    )
}

export default ProfilePage
