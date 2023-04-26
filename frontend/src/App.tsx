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
                </Routes>
            </ThemeProvider>
        </RecoilRoot>
    )
}

export default App
