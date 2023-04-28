import React, { useState } from 'react'

interface Props {
    children: React.ReactNode
}

export const ThemeContext = React.createContext({
    theme: 'light',
    toggleTheme: () => {},
})

export default function ThemeProvider({ children }: Props) {
    const [theme, setTheme] = useState('light')

    const toggleTheme = () => {
        setTheme((theme) => {
            return theme === 'light' ? 'dark' : 'light'
        })
    }

    return (
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    )
}
