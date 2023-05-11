import React, { useEffect, useRef, useState } from 'react'
import styles from './CreateClubForm.module.scss'
import { useNavigate } from 'react-router-dom'
import { checkClubName, getLocationCode, createClub } from 'apis/services/clubs'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import PageHeader from 'components/common/PageHeader'
import LabelInput from 'components/common/LabelInput'
import LabelTextArea from 'components/common/LabelTextArea'
import SelectBox from 'components/common/SelectBox'
import Label from 'components/common/Label'
import { useQueryClient } from '@tanstack/react-query'

type locationCode = {
  dongCode: string
  sidoName?: string
  gugunName?: string
}

type Option = {
  value: string
  label: string
}

function CreateClubForm() {
  const navigate = useNavigate()
  const client = useQueryClient()

  const [name, setName] = useState('')
  const [isNamePass, setIsNamePass] = useState(false)
  const [description, setDescription] = useState('')
  const [sidoOptions, setSidoOptions] = useState<Option[]>([])
  const [sidoCode, setSidoCode] = useState('')
  const [gugunOptions, setGugunOptions] = useState<Option[]>([])
  const [gugunCode, setGugunCode] = useState('')

  const sidoRef = useRef<HTMLDivElement>(null)
  const gugunRef = useRef<HTMLDivElement>(null)

  // 첫 마운트 시 시도 코드 가져오기
  useEffect(() => {
    getLocationCode('sido').then((res) => {
      // 받은 location 배열 정보를 Dropdown 컴포넌트에 option으로 내려줄 수 있도록 가공해서 저장
      const sidoInfo: locationCode[] = res.data.result
      const options: Option[] = []
      sidoInfo.map(({ dongCode, sidoName = '' }) => {
        options.push({
          value: dongCode,
          label: sidoName,
        })
      })
      setSidoOptions(options)
    })
  }, [])

  // 시도 선택에 따른 구군 코드 업데이트
  useEffect(() => {
    getLocationCode('gugun', sidoCode).then((res) => {
      // 받은 location 배열 정보를 Dropdown 컴포넌트에 option으로 내려줄 수 있도록 가공해서 저장
      const gugunInfo: locationCode[] = res.data.result
      const options: Option[] = []
      gugunInfo.map(({ dongCode, gugunName = '' }) => {
        options.push({
          value: dongCode,
          label: gugunName,
        })
      })
      setGugunOptions(options)
    })
  }, [sidoCode])

  // input태그 변화에 따른 모임이름(name) 업데이트하는 함수
  function onChangeSetName(event: React.ChangeEvent<HTMLInputElement>) {
    setName(event.target.value)
  }

  // input태그 변화에 따른 모임설명(description) 업데이트하는 함수
  function onChangeSetDescription(
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) {
    setDescription(event.target.value)
  }

  // 클릭 시, 모임이름 중복 체크 api 요청
  function onClickCheckName() {
    if (!name.trim()) {
      toast.addMessage('error', '모임 이름을 입력해주세요')
      return
    }
    checkClubName(name)
      .then(() => {
        toast.addMessage('success', '중복확인 되었습니다.')
        setIsNamePass(true)
      })
      .catch((err) => {
        toast.addMessage('error', err.data.message)
      })
  }

  // 클릭 시, 모임 생성 api 요청
  function onClickCreateClub() {
    if (!isNamePass || !description.trim() || !gugunCode) {
      toast.addMessage('error', '입력 정보를 확인해주세요')
      return
    }
    createClub(name, description, gugunCode).then((res) => {
      toast.addMessage('success', '모임을 생성했습니다')
      client.invalidateQueries(['user'])
      const clubId = res.data.result.clubId
      navigate(`/club/${clubId}/main`)
    })
  }

  return (
    <div className={styles.container}>
      <PageHeader title="모임생성" url="/club/none" color="primary" />
      <div className={styles.flexbox}>
        <LabelInput
          label="모임이름"
          value={name}
          onChange={onChangeSetName}
          disabled={isNamePass ? true : false}
        />
        <Button
          text="중복"
          color={name.trim() ? 'primary' : 'gray'}
          size="md"
          onClick={onClickCheckName}
          disabled={isNamePass ? true : false}
        />
      </div>
      <LabelTextArea
        label="소개문구"
        value={description}
        onKeyDown={(event: React.KeyboardEvent) => {
          if (event.key === 'Enter') {
            event?.preventDefault()
            sidoRef.current && sidoRef.current.focus()
          }
        }}
        onChange={onChangeSetDescription}
      />
      <div className={styles.location}>
        <Label label="지역선택" />
        <div className={styles.dropdown}>
          <SelectBox
            ref={sidoRef}
            options={sidoOptions}
            defaultLabel="시/도 선택"
            setValue={setSidoCode}
          />
          <SelectBox
            ref={gugunRef}
            options={gugunOptions}
            defaultLabel="구/군 선택"
            setValue={setGugunCode}
          />
        </div>
      </div>
      <div className={styles.buttons}>
        <Button
          text="생성하기"
          color={
            !isNamePass || description.trim() || !gugunCode ? 'gray' : 'primary'
          }
          onClick={onClickCreateClub}
        />
        <Button
          text="취소"
          color="white"
          onClick={() => navigate('/club/none')}
        />
      </div>
    </div>
  )
}

export default CreateClubForm
