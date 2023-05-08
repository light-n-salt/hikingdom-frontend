import React, { useContext, useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ClubMainPage.module.scss'
import { getClubInfo } from 'apis/services/clubs'
import { ClubDetailInfo } from 'types/club.interface'
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
  const [clubInfo, setClubInfo] = useState<ClubDetailInfo>()

  function onChangeSetValue(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  const clubId = useParams<string>().clubId

  useEffect(() => {
    getClubInfo(Number(clubId)).then((res) => setClubInfo(res.data.result))
  }, [])

  return clubInfo ? (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <DeleteModal
            title="모임을 탈퇴하시겠습니까?"
            content="탈퇴한 모임은 다시 가입을 신청해야 합니다."
            buttonText="모임 탈퇴"
            onClickDelete={() => console.log('탈퇴')}
            onClickCloseModal={() => setIsOpen(false)}
          />
        </Modal>
      )}
      <div className={`page p-sm ${theme} ${styles.page}`}>
        <ClubRecordInfo
          participationRate={clubInfo.participationRate}
          totalDuration={clubInfo.totalDuration}
          totalDistance={clubInfo.totalDistance}
          totalAlt={clubInfo.totalAlt}
        />
        <div className={styles.intro}>
          <MeetupIntroduction content={clubInfo.description} />
          <SearchBar
            value={value}
            placeholder="등산했던 산을 검색해보세요"
            onChange={onChangeSetValue}
          />
        </div>
        <img src={clubmountain} className={styles.image} />
        <div className={styles.button}>
          <TextButton
            text="모임탈퇴"
            size="sm"
            color="red"
            onClick={() => setIsOpen(true)}
          />
        </div>
      </div>
    </>
  ) : (
    <div>Loading....</div>
  )
}

export default ClubMainPage
