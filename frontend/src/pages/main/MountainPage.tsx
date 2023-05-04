import React, { useContext, useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Outlet } from 'react-router'
import { ThemeContext } from 'styles/ThemeProvider'

import SearchBar from 'components/common/SearchBar'

import SearchMtPage from './SearchMtPage'
import MainPage from './MainPage'
import MtList from 'components/common/MtList'
import useDebounce from 'hooks/useDebounce'
import { getMountains } from 'apis/services/mountains'

function MountainPage() {
  const { theme } = useContext(ThemeContext)

  const [value, setValue] = useState('')
  const [mtInfoArray, setMtInfoArray] = useState([])
  const debouncedValue = useDebounce(value)

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  useEffect(() => {
    getMountains(debouncedValue)
      .then((res) => {
        setMtInfoArray(res.data)
      })
      .catch(() => {})
  }, [debouncedValue])

  return (
    <div className={`page ${theme} p-md`}>
      <SearchBar
        value={value}
        placeholder="산을 검색해보세요"
        onChangeText={onChange}
      />
      {value ? <MtList mtInfoArray={mtInfoArray} size="lg" /> : <MainPage />}
    </div>
  )
}

export default MountainPage

const mtInfoArray = [
  {
    mountainId: 1,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 2,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 3,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 4,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 15,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 16,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
  {
    mountainId: 17,
    name: '도봉산',
    maxAlt: 123,
    address: '서울시 노원구',
    imgUrl:
      'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
  },
]
