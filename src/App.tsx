import './App.scss'
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Main from 'pages/Main'
import LoginPage from 'pages/auth/LoginPage'
import SignupPage from 'pages/auth/SignupPage'
import WebMargin from 'components/common/WebMargin'

function App() {
    return (
        <Routes>
            <Route path="/" element={<WebMargin />}>
                <Route path="" element={<Main />} />
                <Route path="login" element={<LoginPage />} />
                <Route path="signup" element={<SignupPage />} />
                {/* <Route path="/agreement" element={<AgreementPage />} />
                    <Route path="/password" element={<FindPwdPage />} />
                    
                    // 메인
                    <Route path="/main" element={<MainPage />} />
                    <Route path="/mountain/search" element={<SearchMtPage />} />
                    <Route path="/mountain/detail/:id" element={<MtDetailPage />} />
                    
                    // 순위
                    <Route path="/rank" element={<RankPage />} />
                    
                    // 모임
                    <Route path="/club/search" element={<SearchClubPage />} />
                    <Route path="/club/detail/:id" element={<MainDetailPage />} />
                    <Route path="/club/create" element={<ClubCreatePage />} />
                    <Route path="/club/chat" element={<ClubChatPage />} />
                    <Route path="/club" element={<ClubPage />}>
                    <Route path="main" element={<ClubMainPage />} />
                    <Route path="meetup" element={<ClubMeetupPage />} />
                    <Route path="member" element={<ClubMemberPage />} />
                    <Route path="album" element={<ClubAlbumPage />} />
                    </Route>
                    <Route
                    path="/club/meetup/create"
                    element={<MeetupCreatePage />}
                    />
                    <Route
                        path="/club/meetup/detail/:id"
                        element={<MeetupDetailPage />}
                        />
                        
                        // 마이페이지
                        <Route path="/profile" element={<ProfilePage />} />
                        <Route path="/profile/update" element={<ProfileUpdatePage />} />
                    <Route path="/alarm" element={<AlarmPage />} /> */}
            </Route>
        </Routes>
    )
}

export default App
