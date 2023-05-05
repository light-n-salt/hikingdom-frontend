import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainPage.module.scss'

import clubmountain from 'assets/images/clubmountain.png'
import ClubRecordInfo from 'components/club/ClubRecordInfo'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import SearchBar from 'components/common/SearchBar'
import TextButton from 'components/common/TextButton'
import Modal from 'components/common/Modal'
import DeleteModal from 'components/club/DeleteModal'

function ClubMainPage() {
  const { theme } = useContext(ThemeContext)
  const [value, setValue] = useState('')

  const [isOpen, setIsOpen] = useState(false)

  return (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <DeleteModal 
            title="모임을 탈퇴하시겠습니까?"
            content= "탈퇴한 모임은 다시 가입을 신청해야 합니다."
            buttonText= "모임 탈퇴"
            onClickDelete= {() => console.log("탈퇴")}
            onClickCloseModal= {() => setIsOpen(false)}
          />
        </Modal>
      )}
      <div className={`page p-sm ${theme} ${styles.page}`}>
        <ClubRecordInfo
          participationRate="36.3"
          totalDuration="24"
          totalDistance={81}
          totalAlt={1580}
        />
        <div className={styles.intro}>
          <MeetupIntroduction content={'마리아~ 산타마리아'} />
          <SearchBar
            value={value}
            placeholder="등산했던 산을 검색해보세요"
            onChangeText={setValue}
          />
        </div>
        <img src={clubmountain} className={styles.image} />
        <div className={styles.button}>
          <TextButton text="모임탈퇴" size="sm" color="red" onClick={() => setIsOpen(true)}/>
        </div>
      </div>
    </>
  )
}

export default ClubMainPage
