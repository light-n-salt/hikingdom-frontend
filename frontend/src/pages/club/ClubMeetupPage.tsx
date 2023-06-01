import React, { useState, useEffect } from 'react'
import styles from './ClubMeetupPage.module.scss'
import Calendar from 'components/club/Calendar'
import MeetupList from 'components/club/MeetupList'
import { useDateMeetupsQuery, useMonthMeetupsQuery } from 'apis/services/clubs'
import { MeetupInfo } from 'types/meetup.interface'
import { format } from 'date-fns'
import useUserQuery from 'hooks/useUserQuery'

function ClubMeetupPage() {
  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const [monthMeetups, setMonthMeetups] = useState([])
  const [dateMeetups, setDateMeetups] = useState<MeetupInfo[]>([])

  // 오늘 계산
  const today = new Date()
  const [month, setMonth] = useState(format(today, 'yyyy-MM'))
  const [date, setDate] = useState(format(today, 'yyyy-MM-dd'))

  // 월별 일정 조회 refetch 생성
  const { refetch: getMonthMeetups } = useMonthMeetupsQuery(clubId || 0, month)

  // 월별 일정을 가져오는 함수
  function onChangeGetMonthMeetups(month: string) {
    setMonth(month)
  }

  // month 변화시 월별 일정 조회 refetch 동작
  useEffect(() => {
    getMonthMeetups().then((res) => {
      setMonthMeetups(res.data.startAt)
    })
  }, [month])

  // 일별 일정 조회 refetch 생성
  const { refetch: getDateMeetups } = useDateMeetupsQuery(clubId || 0, date)

  // 일별 일정을 가져오는 함수
  function onClickGetDateMeetups(date: string) {
    setDate(date)
  }

  // date 변화시 일별 일정 조회 refetch 동작
  useEffect(() => {
    getDateMeetups().then((res) => {
      setDateMeetups(res.data)
    })
  }, [date])

  return (
    <div className={`page p-md ${styles.container}`}>
      <Calendar
        today={today}
        meetups={monthMeetups}
        onChangeMonth={onChangeGetMonthMeetups}
        onClickDate={onClickGetDateMeetups}
      />
      <MeetupList meetupInfoArray={dateMeetups} />
    </div>
  )
}

export default ClubMeetupPage
