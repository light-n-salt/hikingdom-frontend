import React, { useState, useEffect } from 'react'
import useDebounce from './useDebounce'

type CheckPwProps = {
  password: string
}

type CheckPwReturns = {
  value: string
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
  isPass: boolean // 비밀번호 일치 여부
}

// 비밀번호 일치 여부 확인 함수
// password : 일치 여부 판단 기준 값
function useCheckPw({ password }: CheckPwProps): CheckPwReturns {
  const [value, setValue] = useState<string>('') // 실시간 입력값 반영
  const [isPass, setIsPass] = useState(false)
  const debouncedValue = useDebounce(value) // delay 후 debouncedValue에 할당

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(event.target.value)
  }

  useEffect(() => {
    // 기준 값과 입력 값 일치 여부 판단 (boolean)
    setIsPass(debouncedValue !== '' && password === debouncedValue)
  }, [debouncedValue])

  // 실시간 입력값, 입력 감지 함수, 일치 여부 반환
  return { value, onChange, isPass }
}

export default useCheckPw
