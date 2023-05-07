import React, { useState } from 'react'
import styles from './ClubMeetupPage.module.scss'
import Calendar from 'components/club/Calendar'
import MeetupList from 'components/club/MeetupList'
import { getMonthMeetups, getDateMeetups } from 'apis/services/clubs'
import { useParams } from 'react-router-dom'
import { MeetupInfo } from 'types/meetup.interface'

function ClubMeetupPage() {
  const { clubId } = useParams()

  const [monthMeetups, setMonthMeetups] = useState(monthEx)
  const [dateMeetups, setDateMeetups] = useState<MeetupInfo[]>(meetupInfoEx)

  function onChangeGetMonthMeetups(month: string) {
    if (!clubId) return
    getMonthMeetups(clubId, month).then((res) => {
      setMonthMeetups(res.data.result)
    })
  }

  function onClickGetDateMeetups(date: string) {
    if (!clubId) return
    getDateMeetups(clubId, date).then((res) => {
      setDateMeetups(res.data.result)
    })
  }

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

const monthEx = [15, 17]
const meetupInfoEx = [
  {
    description: '아주 좋아용',
    meetupHostId: 1,
    meetupId: 2,
    meetupName: '우리 모임',
    mountainName: '관악산',
    startAt: '2014-02-23 12:23',
    totalMmember: 5,
  },
  {
    description: '아주 좋아용 하라랄라라 에그타르트',
    meetupHostId: 3,
    meetupId: 4,
    meetupName: '최고당',
    mountainName: '도봉산',
    startAt: '2014-02-23 2:23',
    totalMmember: 154,
  },
  {
    description: '아주 좋아용 하라랄라라 에그타르트',
    meetupHostId: 3,
    meetupId: 7,
    meetupName: '최고당',
    mountainName: '도봉산',
    startAt: '2014-02-23 2:23',
    totalMmember: 154,
  },
]
