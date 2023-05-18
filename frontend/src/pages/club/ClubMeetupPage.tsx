import React, { useState, useEffect } from 'react'
import styles from './ClubMeetupPage.module.scss'
import Calendar from 'components/club/Calendar'
import MeetupList from 'components/club/MeetupList'
import { getMonthMeetups, getDateMeetups } from 'apis/services/clubs'
import { MeetupInfo } from 'types/meetup.interface'
import { format } from 'date-fns'
import useUserQuery from 'hooks/useUserQuery'

function ClubMeetupPage() {
  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId?.toString()

  const [monthMeetups, setMonthMeetups] = useState([])
  const [dateMeetups, setDateMeetups] = useState<MeetupInfo[]>([])

  // 월별 일정을 가져오는 함수
  function onChangeGetMonthMeetups(month: string) {
    if (!clubId) return
    getMonthMeetups(clubId, month).then((res) => {
      setMonthMeetups(res.data.result.startAt)
    })
  }

  // 일별 일정을 가져오는 함수
  function onClickGetDateMeetups(date: string) {
    if (!clubId) return
    getDateMeetups(clubId, date).then((res) => {
      setDateMeetups(res.data.result)
    })
  }

  // 마운트 시 오늘 일정 가져오기
  useEffect(() => {
    const today = new Date()
    const stringToday = format(today, 'yyyy-MM-dd')
    onClickGetDateMeetups(stringToday)
  }, [])

  return (
    <div className={`page p-md ${styles.container}`}>
      <Calendar
        today={new Date()}
        meetups={monthMeetups}
        onChangeMonth={onChangeGetMonthMeetups}
        onClickDate={onClickGetDateMeetups}
      />
      <MeetupList meetupInfoArray={dateMeetups} />
    </div>
  )
}

export default ClubMeetupPage
