import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
// import styles from './Main.module.scss'

// import UserProfile from 'components/user/UserProfile'

export default function Main() {
    const { theme, toggleTheme } = useContext(ThemeContext)
    // const userRecord = {
    //     totalHikingCount: 10,
    //     totalMountainCount: 30,
    //     totalDuration: '30:23',
    //     totalDistance: 34,
    //     totalAlt: 34,
    // }

    return (
        <div className={`page-gradation ${theme} mobile`}>
            <button onClick={toggleTheme}>다크모드</button>

            {/* <UserProfile
                imgUrl="https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80"
                nickname="이병호리병"
                email="조혜진진자라@gamil.com"
                userRecord={userRecord}
            /> */}
        </div>
    )
}
