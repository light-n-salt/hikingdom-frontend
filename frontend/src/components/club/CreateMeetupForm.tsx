import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import LabelInput from 'components/common/LabelInput'
import yellowLabel from 'assets/images/yellow_label.png'
import mail from 'assets/images/mail.png'
import calendar from 'assets/images/calendar.png'
import mountain from 'assets/images/mountain.png'
import LabelTextArea from 'components/common/LabelTextArea'
import Dropdown from 'components/common/Dropdown'
import LabelInputSelect from 'components/common/LabelInputSelect'
import { getMountains } from 'apis/services/mountains'
import { createMeetup } from 'apis/services/clubs'
import styles from './CreateMeetupForm.module.scss'
import Button from 'components/common/Button'
import { useNavigate, useParams } from 'react-router-dom'
import { MtInfo } from 'types/mt.interface'

type Option = {
  value: string
  label: string
}

function CreateMeetupForm() {
  const { clubId } = useParams()

  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [mountain, setMountain] = useState('')
  const [mountainOptions, setMountainOptions] = useState<Option[]>([])
  const [mountainId, setMountainId] = useState('')
  const [date, setDate] = useState('')
  const [time, setTime] = useState('')
  const [description, setDescription] = useState('')

  // input태그 변화에 따른 일정이름(name) 업데이트하는 함수
  function onChangeSetName(event: React.ChangeEvent<HTMLInputElement>) {
    setName(event.target.value)
  }

  // input태그 변화에 따른 일정날짜(date) 업데이트하는 함수
  function onChangeSetMountain(event: React.ChangeEvent<HTMLInputElement>) {
    setMountain(event.target.value)
    if (!event.target.value) return
    getMountains(event.target.value).then((res) => {
      const mountainInfoArray: MtInfo[] = res.data.result.content
      const options: Option[] = []
      mountainInfoArray.map(({ mountainId, name }) => {
        options.push({
          value: mountainId.toString(),
          label: name,
        })
      })
      setMountainOptions(options)
    })
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

  // 클릭 시, 모임을 생성하는 함수
  function onClickCreateMeetup() {
    if (
      !clubId ||
      !name.trim() ||
      !mountainId ||
      !date ||
      !time ||
      !description.trim()
    )
      return
    const startAt = date + ' ' + time
    createMeetup(clubId, name, mountainId, startAt, description).then((res) => {
      const meetupId = res.data.result.id
      navigate(`/club/${clubId}/meetup/${meetupId}/detail`)
    })
  }

  return (
    <div className={styles.container}>
      <LabelInput label="일정 이름" value={name} onChange={onChangeSetName} />
      <LabelInputSelect
        label="산 선택"
        placeholder="산을 검색해주세요"
        value={mountain}
        onChange={onChangeSetMountain}
        options={mountainOptions}
        setInputValue={setMountain}
        setOption={setMountainId}
      />
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
      <Button
        text="일정 등록"
        color={
          !name.trim() || !mountainId || !date || !time || !description.trim()
            ? 'gray'
            : 'primary'
        }
        onClick={onClickCreateMeetup}
      />
    </div>
  )
}

export default CreateMeetupForm
