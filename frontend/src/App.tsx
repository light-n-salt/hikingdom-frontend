import React from 'react'
import logo from './logo.svg'
import './App.scss'
import { RecoilRoot } from 'recoil'
import { Routes, Route } from 'react-router-dom'

function App() {
    return (
        <RecoilRoot>
            <Routes>
                <Route path="/" element={<Main />} />
            </Routes>
        </RecoilRoot>
    )
}

export default App

function Main() {
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                    Edit <code>src/App.tsx</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
            </header>
        </div>
    )
}
