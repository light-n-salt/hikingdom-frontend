import MtList from 'components/common/MtList'
import SearchBar from 'components/common/SearchBar'
// import RankList from 'components/common/RankList'
import React, { useContext, useState, useEffect, useRef } from 'react'
import styles from './SearchMtPage.module.scss'
import { ThemeContext } from 'styles/ThemeProvider'
import { useNavigate, useOutletContext, useLocation } from 'react-router-dom'
import useDebounce from 'hooks/useDebounce'
import { getMountains } from 'apis/services/mountains'
import useInfiniteScroll from 'hooks/useInfiniteScroll'
import Loading from 'components/common/Loading'

type OutletProps = {
  value: string
}

function SearchMtPage() {
  const navigate = useNavigate()

  const location = useLocation()
  const query = location.state.query
  const debouncedQuery = useDebounce(query!)

  const [mtInfoArray, setMtInfoArray] = useState(mtInfoEx)
  const infiniteRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    const toMainPage = () => navigate('/main')
    window.addEventListener('popstate', toMainPage)
    return () => window.removeEventListener('popstate', toMainPage)
  }, [])

  useEffect(() => {
    if (debouncedQuery) {
      getMountains(debouncedQuery)
        .then((res) => {
          setMtInfoArray(res.data.result)
        })
        .catch(() => {})
    }
  }, [debouncedQuery])

  function loadMore() {
    return getMountains(debouncedQuery, mtInfoArray.slice(-1)[0].mountainId)
      .then((res) => {
        setMtInfoArray((mtInfoArray) => [...mtInfoArray, ...res.data.result])
      })
      .catch(() => {})
  }

  const { isLoading } = useInfiniteScroll(infiniteRef, loadMore)

  return (
    <div ref={infiniteRef} className={styles.container}>
      <MtList mtInfoArray={mtInfoArray} size="lg" />
      {isLoading && (
        <div className={styles.loading}>
          <Loading size="sm" />
        </div>
      )}
    </div>
  )
}

export default SearchMtPage

const mtInfoEx = [
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
