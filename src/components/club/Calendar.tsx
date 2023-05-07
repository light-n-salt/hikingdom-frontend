import React from 'react'
import styles from './Calendar.module.scss'

// type CalendarProps ={
//     currentDate: Date
//     currentMonth:

// }

function Calendar() {
  return (
    <div className={styles.container}>
      <div className={styles.flexbox}>
        <input type="month" />
      </div>
      <div></div>
    </div>
  )
}

export default Calendar
