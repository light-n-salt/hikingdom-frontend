import React, { useState, useMemo, useEffect } from 'react'
import styles from './CreateMeetupForm.module.scss'
import { useInfiniteMountainsQuery } from 'apis/services/mountains'
import { useCreateMeetup } from 'apis/services/clubs'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import LabelTextArea from 'components/common/LabelTextArea'
import LabelInputSelect from 'components/common/LabelInputSelect'
import useUserQuery from 'hooks/useUserQuery'
import useDebounce from 'hooks/useDebounce'

type Option = {
  value: string
  label: string
}

function CreateMeetupForm() {
  const { data: userInfo } = useUserQuery()
  const clubId = userInfo?.clubId

  const [name, setName] = useState('')
  const [mountainId, setMountainId] = useState('')
  const [date, setDate] = useState('')
  const [time, setTime] = useState('')
  const [description, setDescription] = useState('')
  const [query, setQuery] = useState('')

  // input태그 변화에 따른 일정이름(name) 업데이트하는 함수
  function onChangeSetName(event: React.ChangeEvent<HTMLInputElement>) {
    setName(event.target.value)
  }

  const { refetch: getMountains, data } = useInfiniteMountainsQuery(query)

  // input태그 변화에 따른 산 목록(mountainOptions)을 업데이트 하는 함수
  function onChangeSetMountain(event: React.ChangeEvent<HTMLInputElement>) {
    setQuery(event.target.value)
    getMountains()
  }

  const mountainOptions = useMemo(() => {
    if (!data) return []
    const options: Option[] = []
    data.pages.flatMap((page) => {
      page.content.map((element: any) => {
        options.push({
          value: String(element.mountainId),
          label: element.name,
        })
      })
    })
    return options
  }, [data])

  // input태그 변화에 따른 일정날짜(date) 업데이트하는 함수
  function onChangeSetDate(event: React.ChangeEvent<HTMLInputElement>) {
    setDate(event.target.value)
    setQuery(event.target.value)
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

  const { mutate: createMeetup } = useCreateMeetup(clubId || 0)

  // 클릭 시, 모임을 생성하는 함수
  function onClickCreateMeetup() {
    if (
      !clubId ||
      !name.trim() ||
      !mountainId ||
      !date ||
      !time ||
      !description.trim()
    ) {
      toast.addMessage('error', '모든 항목을 정확하게 기입해주세요')
      return
    }
    const [year, month, day] = date
      .split('-')
      .map((string: string) => parseInt(string))

    const [hours, minutes] = time
      .split(':')
      .map((string: string) => parseInt(string))

    if (new Date(year, month - 1, day, hours, minutes) < new Date()) {
      toast.addMessage('error', '과거 시간에는 일정을 생성할 수 없습니다')
      return
    }
    const startAt = date + ' ' + time

    // mutate 함수에 인자 전달
    createMeetup({ name, mountainId, startAt, description })
  }

  return (
    <div className={styles.container}>
      <LabelInput label="일정 이름" value={name} onChange={onChangeSetName} />
      <LabelInputSelect
        label="산 선택"
        placeholder="산을 검색해주세요"
        value={query}
        onChange={onChangeSetMountain}
        options={mountainOptions}
        setInputValue={setQuery}
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
