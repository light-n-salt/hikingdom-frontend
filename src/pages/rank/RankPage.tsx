import React, { useContext, useEffect, useState, useRef } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './RankPage.module.scss'
import trophy from 'assets/images/trophy.png'
import {BiSearch} from 'react-icons/bi'
import IconButton from 'components/common/IconButton'
import { useNavigate } from 'react-router-dom'
import SelectBox from 'components/common/SelectBox'
import Dropdown from 'components/common/Dropdown'
import RankList from 'components/common/RankList'
import {getRanking} from 'apis/services/clubs'

const filterOptions = [
  {label:'종합랭킹 순', value:''}, {label:'참여도 순', value:'participation'}, {label:'거리 순', value:'distance'}, {label:'시간 순', value:'time'}
]

function RankPage() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)
  const [ filter, setFilter ] = useState('')
  const [ clubInfoArray, setClubInfoArray ] = useState([])
  const infiniteRef = useRef(null)

  useEffect(() => {
    getRanking(filter)
    .then((res) => {
      setClubInfoArray(res.data.result)
    })
  }, [filter])

  return (<div className={`page ${theme}`}>
    <div className={styles.header}>
      <img className={styles.icon} src={trophy}/>
      <h2>모임별 랭킹</h2>
      <div className={styles.search}>
        <IconButton icon={<BiSearch/>} color="primary" size="md" onClick = {()=>navigate('/club/search')}/>
      </div>
    </div>
    <div className={styles.select}>
      <Dropdown options={filterOptions} onChange={setFilter}/>
    </div>
    <div ref={infiniteRef} className={styles.clubs}>
      <RankList clubInfoArray={clubInfoArray}/>
    </div>
  </div>)
}

export default RankPage
