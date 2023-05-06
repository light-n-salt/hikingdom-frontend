import React, { useState, useEffect } from 'react'
import checkReg from 'utils/checkReg'
import useDebounce from './useDebounce'

type AuthInputProps = {
  type: 'email' | 'nickname' | 'password' | 'code'
  initialValue?: string
}

type AuthInputReturns = {
  value: string
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
  isPass: boolean
  condition: string
}

function useAuthInput({
  type,
  initialValue = '',
}: AuthInputProps): AuthInputReturns {
  const [value, setValue] = useState<string>(initialValue)
  const [isPass, setIsPass] = useState(false)
  const [condition, setCondition] = useState('') // 정규식 통과 조건
  const debouncedValue = useDebounce(value)

  function onChange(event: React.ChangeEvent<HTMLInputElement>) {
    setValue(event.target.value)
  }

  useEffect(() => {
    const { result, condition } = checkReg(type, debouncedValue)
    setIsPass(result)
    setCondition(condition)
  }, [debouncedValue])

  return { value, onChange, isPass, condition }
}

export default useAuthInput
