import './App.scss'
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import WebMargin from 'components/common/WebMargin'
// Auth
import IndexPage from 'pages/auth/IndexPage'
import LoginPage from 'pages/auth/LoginPage'
import AgreePage from 'pages/auth/AgreePage'
import FindPwPage from 'pages/auth/FindPwPage'
import SignupPage from 'pages/auth/SignupPage'
import PolicyPage from 'pages/auth/PolicyPage'
// Main
import SearchLayout from 'pages/main/SearchLayout'
import MainPage from 'pages/main/MainPage'
import SearchMtPage from 'pages/main/SearchMtPage'
import MtDetailPage from 'pages/main/MtDetailPage'
// Rank
import RankPage from 'pages/rank/RankPage'
// Club
import SearchClubPage from 'pages/club/SearchClubPage'
import MainDetailPage from 'pages/club/ClubMainDetailPage'
import ClubCreatePage from 'pages/club/ClubCreatePage'
import ClubChatPage from 'pages/club/ClubChatPage'
import ClubNoneExistPage from 'pages/club/ClubNoneExistPage'
// Club - main
import ClubPage from 'pages/club/ClubPage'
import ClubMainPage from 'pages/club/ClubMainPage'
import ClubMeetupPage from 'pages/club/ClubMeetupPage'
import ClubMemberPage from 'pages/club/ClubMemberPage'
import ClubAlbumPage from 'pages/club/ClubAlbumPage'
// Club - Meetup
import CreateMeetupPage from 'pages/club/CreateMeetupPage'
import MeetupDetailPage from 'pages/club/MeetupDetailPage'
// MyPage
import ProfilePage from 'pages/profile/ProfilePage'
import ProfileUpdatePage from 'pages/profile/ProfileUpdatePage'
import AlarmPage from 'pages/profile/AlarmPage'
import NotFoundPage from 'pages/NotFoundPage'
import PrivateRoute from 'pages/PrivateRoute'

function App() {
  return (
    <Routes>
      <Route path="/" element={<WebMargin />}>
        <Route path="" element={<IndexPage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="signup" element={<SignupPage />} />
        <Route path="agreement" element={<AgreePage />} />
        <Route path="password" element={<FindPwPage />} />
        <Route path="policy" element={<PolicyPage />} />
        <Route element={<PrivateRoute />}>
          {/* 메인 */}
          <Route path="/main" element={<SearchLayout />}>
            <Route path="" element={<MainPage />} />
            <Route path="search" element={<SearchMtPage />} />
          </Route>
          <Route
            path="/mountain/:mountainId/detail"
            element={<MtDetailPage />}
          />
          {/* 순위 */}
          <Route path="/rank" element={<RankPage />} />
          {/* 모임 */}
          <Route path="/club/search" element={<SearchClubPage />} />
          <Route path="/club/create" element={<ClubCreatePage />} />
          <Route path="/club/:clubId/detail" element={<MainDetailPage />} />
          <Route path="/club/:clubId/chat" element={<ClubChatPage />} />
          <Route path="/club/none" element={<ClubNoneExistPage />} />
          <Route path="/club/:clubId" element={<ClubPage />}>
            <Route path="main" element={<ClubMainPage />} />
            <Route path="meetup" element={<ClubMeetupPage />} />
            <Route path="member" element={<ClubMemberPage />} />
            <Route path="album" element={<ClubAlbumPage />} />
          </Route>
          <Route
            path="/club/:clubId/meetup/create"
            element={<CreateMeetupPage />}
          />
          <Route
            path="/club/:clubId/meetup/:meetupId/detail"
            element={<MeetupDetailPage />}
          />
          {/* 마이페이지 */}
          <Route path="/profile/:nickname" element={<ProfilePage />} />
          <Route path="/profile/update" element={<ProfileUpdatePage />} />
          <Route path="/alarm" element={<AlarmPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App
