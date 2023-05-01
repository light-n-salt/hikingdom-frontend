import './App.scss'
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import ThemeProvider from 'styles/ThemeProvider'

// import Main from 'pages/Main'
// Auth
import LoginPage from 'pages/auth/LoginPage'
import AgreementPage from 'pages/auth/AgreementPage'
import FindPwPage from 'pages/auth/FindPwPage'
import SignupPage from 'pages/auth/SignupPage'

// Main
import MainPage from 'pages/main/MainPage'
import SearchMtPage from 'pages/main/SearchMtPage'
import MtDetailPage from 'pages/main/MtDetailPage'

// Rank
import RankPage from 'pages/rank/RankPage'

// Club
import SearchClubPage from 'pages/club/SearchClubPage'
import MainDetailPage from 'pages/club/MainDetailPage'
import ClubCreatePage from 'pages/club/ClubCreatePage'
import ClubChatPage from 'pages/club/ClubChatPage'
// Club -
import ClubPage from 'pages/club/ClubPage'
import ClubMainPage from 'pages/club/ClubMainPage'
import ClubMeetupPage from 'pages/club/ClubMeetupPage'
import ClubMemberPage from 'pages/club/ClubMemberPage'
import ClubAlbumPage from 'pages/club/ClubAlbumPage'
// Club - Meetup
import MeetupCreatePage from 'pages/club/MeetupCreatePage'
import MeetupDetailPage from 'pages/club/MeetupDetailPage'

// MyPage
import ProfilePage from 'pages/profile/ProfilePage'
import ProfileUpdatePage from 'pages/profile/ProfileUpdatePage'
import AlarmPage from 'pages/profile/AlarmPage'

import WebMargin from 'components/common/WebMargin'

function App() {
    return (
        <Routes>
            <Route path="/" element={<WebMargin />}>
                {/* <Route path="" element={<Main />} /> */}
                <Route path="login" element={<LoginPage />} />
                <Route path="signup" element={<SignupPage />} />
                <Route path="/agreement" element={<AgreementPage />} />
                <Route path="/password" element={<FindPwPage />} />

                {/* 메인 */}
                <Route path="" element={<MainPage />} />
                <Route path="/mountain/search" element={<SearchMtPage />} />
                <Route path="/mountain/detail/:id" element={<MtDetailPage />} />

                {/* 순위 */}
                <Route path="/rank" element={<RankPage />} />

                {/* 모임 */}
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

                {/* 마이페이지 */}
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/profile/update" element={<ProfileUpdatePage />} />
                <Route path="/alarm" element={<AlarmPage />} />
            </Route>
        </Routes>
    )
}

export default App
