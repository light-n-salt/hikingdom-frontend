import React, { useContext, MouseEvent, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import { useNavigate } from 'react-router-dom'
import styles from './PastMeetupItem.module.scss'

import IconText from 'components/common/IconText'

import { BiCalendarAlt } from 'react-icons/bi'
import { AiOutlineClockCircle } from 'react-icons/ai'
import { FiChevronRight } from 'react-icons/fi'

import mountain from 'assets/images/mountain.png'
import time from 'assets/images/hourglass.png'
import distance from 'assets/images/shoe.png'
import height from 'assets/images/hot_air_balloon.png'

import TrackingInfo from './TrackingInfo'
import Modal from 'components/common/Modal'

import { UserHiking } from 'types/user.interface'
import { convertToKm } from 'utils/convertToKm'
import { convertToTime } from 'utils/convertToTime'
import useUserQuery from 'hooks/useUserQuery'

export default function PastMeetupItem({ hiking }: { hiking: UserHiking }) {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const [isOpen, setIsOpen] = useState(false)
  const { data: userInfo } = useUserQuery()

  // 일정 상세보기로 이동하는 함수
  const onClickOpenModal = () => {
    setIsOpen(true)
  }

  // 그룹 일정 상세보기로 이동하는 함수
  const onClickGroup = (e: MouseEvent<HTMLDivElement>) => {
    e.stopPropagation() // 부모 이벤트 버블링 방지
    navigate(`/club/meetup/${hiking.meetupId}/detail`)
  }

  return (
    <>
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <TrackingInfo hikingRecordId={hiking.hikingRecordId} />
        </Modal>
      )}
      <div
        className={`content ${theme} ${styles.container}`}
        onClick={onClickOpenModal}
      >
        <div className={styles.title}>
          <IconText
            imgSrc={mountain}
            text={hiking.mountainName}
            size="md"
            isBold={true}
          />

          {/* 그룹 스케줄일 때 상세보기 버튼 */}
          {hiking.isMeetup && (
            <div className={styles.group} onClick={onClickGroup}>
              {hiking.meetupName} <FiChevronRight />
            </div>
          )}
        </div>

        <div className={styles.flexbox}>
          <IconText
            icon={<BiCalendarAlt />}
            text={hiking.startAt.split(' ')[0].replaceAll('-', '.').slice(2)}
            size="sm"
          />
          <IconText
            icon={<AiOutlineClockCircle />}
            text={hiking.startAt.split(' ')[1].slice(0, -3)}
            size="sm"
          />
        </div>
        <div className={styles.flexbox}>
          <IconText
            imgSrc={time}
            text={convertToTime(hiking.totalDuration)}
            size="sm"
          />
          <IconText
            imgSrc={distance}
            text={convertToKm(hiking.totalDistance) + 'km'}
            size="sm"
          />
          <IconText imgSrc={height} text={`${hiking.maxAlt} m`} size="sm" />
        </div>
      </div>
    </>
  )
}
