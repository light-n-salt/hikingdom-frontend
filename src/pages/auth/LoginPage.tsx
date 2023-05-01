import Logo from 'components/common/Logo'
import Loading from 'components/common/Loading'
import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import LoginForm from 'components/auth/LoginForm'

function LoginPage() {
    const { theme, toggleTheme } = useContext(ThemeContext)

    return (
        <div className={`page-gradation ${theme}`}>
            <Logo />
            <LoginForm />
        </div>
    )
}

export default LoginPage

// const clubInfoArray = [
//     {
//         clubId: 1,
//         clubName: '산타마리아',
//         location: '서울시 노원구',
//         totalMember: 23,
//         totalDuration: '12:02',
//         totalDistance: 123,
//         participationRate: 87,
//         ranking: 2,
//     },
//     {
//         clubId: 1,
//         clubName: '산타마리아',
//         location: '서울시 노원구',
//         totalMember: 23,
//         totalDuration: '12:02',
//         totalDistance: 123,
//         participationRate: 87,
//         ranking: 5,
//     },
// ]

// const mtInfoArray = [
//     {
//         mountainId: 1,
//         name: '도봉산',
//         maxAlt: 123,
//         address: '서울시 노원구',
//         imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
//     },
//     {
//         mountainId: 1,
//         name: '도봉산',
//         maxAlt: 123,
//         address: '서울시 노원구',
//         imgUrl: 'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
//     },
// ]
