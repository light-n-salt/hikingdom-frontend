import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'
import LabelInput from 'components/common/LabelInput'
import yellowLabel from 'assets/images/yellow_label.png'
import mail from 'assets/images/mail.png'
import calendar from 'assets/images/calendar.png'
import mountain from 'assets/images/mountain.png'
import LabelTextArea from 'components/common/LabelTextArea'
import Dropdown from 'components/common/Dropdown'

type Option = {
  value: string
  label: string
}

function MeetupCreatePage() {
  const { theme } = useContext(ThemeContext)

  const [name, setName] = useState('')
  const [mountainOptions, setMountainOptions] = useState<Option[]>([])
  const [mountain, setMountain] = useState('')
  const [date, setDate] = useState('')
  const [time, setTime] = useState('')
  const [description, setDescription] = useState('')

  // input태그 변화에 따른 일정이름(name) 업데이트하는 함수
  function onChangeSetName(event: React.ChangeEvent<HTMLInputElement>) {
    setName(event.target.value)
  }

  // input태그 변화에 따른 일정날짜(date) 업데이트하는 함수
  function onChangeSetDate(event: React.ChangeEvent<HTMLInputElement>) {
    setDate(event.target.value)
  }

  // input태그 변화에 따른 일정시각(time) 업데이트하는 함수
  function onChangeSetTime(event: React.ChangeEvent<HTMLInputElement>) {
    setTime(event.target.value)
  }

  // input태그 변화에 따른 일정소개(description) 업데이트하는 함수
  function onChangeSetDescription(
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) {
    setDescription(event.target.value)
  }

  return (
    <div className={`page p-sm ${theme} mobile`}>
      <PageHeader title="일정 만들기" url="/club/meetup" color="primary" />
      <LabelInput label="일정 이름" value={name} onChange={onChangeSetName} />
      {/* <Dropdown options={} defaultLabel="산 선택" setValue={setMountain} /> */}
      <LabelInput
        label="일정 날짜"
        value={date}
        type="date"
        onChange={onChangeSetDate}
      />
      <LabelInput
        label="일정 시각"
        value={time}
        type="time"
        onChange={onChangeSetTime}
      />
      <LabelTextArea
        label="일정 소개"
        value={description}
        onChange={onChangeSetDescription}
      />
    </div>
  )
}

export default MeetupCreatePage
