import React, { useCallback } from 'react'

import { useRecoilState } from 'recoil'

import { themeState } from 'recoil/atoms'

interface Props {
  children: React.ReactNode
}

export const ThemeContext = React.createContext({
  theme: 'light',
  toggleTheme: () => {},
})

export default function ThemeProvider({ children }: Props) {
  const [theme, setTheme] = useRecoilState(themeState)

  const toggleTheme = useCallback(() => {
    setTheme((theme) => {
      return theme === 'light' ? 'dark' : 'light'
    })
  }, [])

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  )
}
