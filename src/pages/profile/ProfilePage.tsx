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

    const userRecord = {
        totalHikingCount: 10,
        totalMountainCount: 30,
        totalDuration: '30:23',
        totalDistance: 34,
        totalAlt: 34,
    }

    const userHikingList = [
        {
            hikingRecordId: 0,
            mountainName: '도봉산',
            startAt: 'YYYY.MM.DD HH:mm:ss',
            totalDuration: '23:00',
            totalDistance: 402,
            maxAlt: 203,
            isGroup: true,
            scheduleId: 3,
            scheduleName: '등산 가즈아',
        },

        {
            hikingRecordId: 1,
            mountainName: '도봉산',
            startAt: 'YYYY.MM.DD HH:mm:ss',
            totalDuration: '23:00',
            totalDistance: 402,
            maxAlt: 203,
            isGroup: false,
            scheduleId: null,
            scheduleName: null,
        },

        {
            hikingRecordId: 2,
            mountainName: '수락산',
            startAt: 'YYYY.MM.DD HH:mm:ss',
            totalDuration: '23:00',
            totalDistance: 402,
            maxAlt: 203,
            isGroup: false,
            scheduleId: null,
            scheduleName: null,
        },
    ]

    return (
        <div className={`page p-sm ${theme} mobile ${styles['profile-page']}`}>
            <IconButton imgSrc={bell} onClick={() => navigate('/alarm')} />
            <UserProfile
                imgUrl="https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80"
                nickname="이병호리병"
                email="조혜진진자라@gamil.com"
                userRecord={userRecord}
            />
            <div>
                <div>등산기록</div>
                <div>최신순</div>
            </div>
            <PastMeetupList hikingList={userHikingList} />
        </div>
    )
}

export default ProfilePage
