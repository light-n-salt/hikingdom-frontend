import './App.scss'
import React from 'react'
import { RecoilRoot } from 'recoil'
import { Routes, Route } from 'react-router-dom'
import Main from 'pages/Main'
import ThemeProvider from 'styles/ThemeProvider'

function App() {
    return (
        <RecoilRoot>
            <ThemeProvider>
                <Routes>
                    <Route path="/" element={<Main />} />
                    {/* <Route path="/login" element={<LoginPage />} />
                    <Route path="/agreement" element={<AgreementPage />} />
                    <Route path="/password" element={<FindPwdPage />} />
                    <Route path="/signup" element={<SignupPage />} />

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
                        <Route path="sched" element={<ClubSchedPage />} />
                        <Route path="member" element={<ClubMemberPage />} />
                        <Route path="album" element={<ClubAlbumPage />} />
                    </Route>
                    <Route
                        path="/club/sched/create"
                        element={<SchedCreatePage />}
                    />
                    <Route
                        path="/club/sched/detail/:id"
                        element={<SchedDetailPage />}
                    />

                    // 마이페이지
                    <Route path="/profile" element={<ProfilePage />} />
                    <Route path="/profile/update" element={<ProfileUpdatePage />} />
                    <Route path="/alarm" element={<AlarmPage />} /> */}
                </Routes>
            </ThemeProvider>
        </RecoilRoot>
    )
}

export default App
