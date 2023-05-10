import React, { useState, useEffect } from 'react'

/*
비밀번호 재확인 커스텀 훅.
비밀번호 재확인 값을 기존 비밀번호와 비교한다.
*/

type CheckPwProps = {
  password: string // 비교할 기존 비밀번호
}

type CheckPwReturns = {
  value: string // 비밀번호 재확인 value
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void // 비밀번호 input태그의 onChange에 따라 value를 djqepdlxm
  isPass: boolean // 비밀번호 일치 여부
}

function useCheckPw({ password }: CheckPwProps): CheckPwReturns {
  const [value, setValue] = useState<string>('') // 비밀번호 재확인 값 useState
  const [isPass, setIsPass] = useState(false) // 기존 비밀번호와 일치 여부

  // 비밀번호 input태그의 onChange에 따라 value 업데이트 하는 함수
  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  // 지연된(debounced) 값이 변화함에 따라서 기존비밀 번호와 일치여부 판단
  useEffect(() => {
    setIsPass(value.trim() !== '' && password === value)
  }, [value])

  // 실시간 입력값, 입력 감지 함수, 일치 여부 반환
  return { value, onChange, isPass }
}

export default useCheckPw
