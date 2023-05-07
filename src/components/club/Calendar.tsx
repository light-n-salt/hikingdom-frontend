import React, { useState, useMemo, useEffect, useContext } from 'react'
import styles from './Calendar.module.scss'
import { FiChevronLeft, FiChevronRight } from 'react-icons/fi'
import {
  format,
  addDays,
  addMonths,
  subMonths,
  startOfMonth,
  endOfMonth,
  startOfWeek,
  endOfWeek,
  isSameMonth,
} from 'date-fns'
import IconButton from 'components/common/IconButton'
import { ThemeContext } from 'styles/ThemeProvider'

const WEEK_DAYS = ['일', '월', '화', '수', '목', '금', '토']

type CalendarProps = {
  today: Date
  meetups: number[]
  onChangeMonth: (month: string) => void
  onClickDate: (date: string) => void
}

function Calendar({
  today,
  meetups,
  onChangeMonth,
  onClickDate,
}: CalendarProps) {
  const { theme } = useContext(ThemeContext)
  // 오늘 날짜 기반으로, 현재 달력이 바라보는 날짜 설정
  const [currentDate, setCurrentDate] = useState(today)

  const { calendarStart, calendarEnd } = useMemo(() => {
    const monthStart = startOfMonth(currentDate) // 현 월의 시작 일자
    const monthEnd = endOfMonth(currentDate) // 현 월의 마지막 일자
    const calendarStart = startOfWeek(monthStart) // 달력의 시작 일자 (전 월 포함)
    const calendarEnd = endOfWeek(monthEnd) // 달력의 마지막 일자 (다음 월 포함)
    return { calendarStart, calendarEnd }
  }, [currentDate])

  // 날짜 변경 시 마다 onChangeMonth 실행
  useEffect(() => {
    onChangeMonth(format(currentDate, 'yyyy-MM'))
  }, [currentDate])

  // 왼쪽 화살표 버튼 클릭 시, currentDate에서 한 달 minus
  function onClickPreviousMonth() {
    setCurrentDate(subMonths(currentDate, 1))
  }

  // 오른쪽 화살표 버튼 클릭 시, currentDate에서 한 달 plus
  function onClickNextMonth() {
    setCurrentDate(addMonths(currentDate, 1))
  }

  // 월 변경 시마다, 해당 월의 첫 일로 setCurrentDate 업데이트
  function onChangeSetCurrentDate(event: React.ChangeEvent<HTMLInputElement>) {
    console.log(event.target.value)
    const [year, month] = event.target.value.split('-')
    const date = new Date(parseInt(year), parseInt(month) - 1)
    console.log(date)
    setCurrentDate(date)
  }

  // 달력 날짜 박스 Array 형성
  const dateboxes = []
  let tempDate = calendarStart
  const todayStringDate = format(today, 'yyyy-MM-dd')
  while (tempDate <= calendarEnd) {
    const day = tempDate.getDay() // 요일: 0이면 일요일, 6이면 토요일
    const date = tempDate.getDate() // 일자
    const stringDate = format(tempDate, 'yyyy-MM-dd')
    const fullDate = tempDate

    // 오늘에 따른 today 클래스
    const todayClass = todayStringDate === stringDate ? styles.today : ''
    // 일정 유무에 따른 meetup 클래스
    const meetupClass = meetups.includes(date) ? styles.meetup : ''
    // 요일에 따른 color 클래스
    const colorClass =
      day === 0 ? styles.sunday : day === 6 ? styles.saturday : ''
    // 월에 따른 background-clor 클래스
    const bgColorClass = !isSameMonth(currentDate, fullDate) ? styles.gray : ''
    // 월에 따른 onClick 함수 다르게 할당
    const onClick = !isSameMonth(currentDate, fullDate)
      ? () => setCurrentDate(fullDate)
      : () => onClickDate(stringDate)

    dateboxes.push(
      <div
        key={stringDate}
        className={`${styles.datebox} ${colorClass} ${bgColorClass} ${todayClass} ${meetupClass}`}
        onClick={onClick}
      >
        <div>{date}</div>
      </div>
    )
    tempDate = addDays(tempDate, 1)
  }

  return (
    <div className={`content ${theme} ${styles.container}`}>
      <div className={styles.flexbox}>
        <IconButton icon={<FiChevronLeft />} onClick={onClickPreviousMonth} />
        <input
          type="month"
          onChange={onChangeSetCurrentDate}
          value={format(currentDate, 'yyyy-MM')}
          className={styles.input}
        />
        <IconButton icon={<FiChevronRight />} onClick={onClickNextMonth} />
      </div>
      <div className={`${styles.gridbox} ${styles.weekdays}`}>
        {WEEK_DAYS.map((day) => (
          <div key={day}>{day}</div>
        ))}
      </div>
      <div className={`${styles.gridbox} ${styles.calendar}`}>{dateboxes}</div>
    </div>
  )
}

export default Calendar
