import React from 'react'
import styles from './RankHeader.module.scss'
import { BiSearch } from 'react-icons/bi'
import { useNavigate } from 'react-router-dom'
import trophy from 'assets/images/trophy.png'
import IconButton from 'components/common/IconButton'

function RankHeader() {
  const navigate = useNavigate()
  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <img className={styles.icon} src={trophy} />
        <h2>모임별 랭킹</h2>
      </div>
      <IconButton
        icon={<BiSearch />}
        color="primary"
        size="md"
        onClick={() => navigate('/club/search')}
      />
    </div>
  )
}

export default RankHeader
