import React, { useState, useEffect } from 'react'
import checkReg from 'utils/checkReg'
import useDebounce from './useDebounce'

type AuthInputProps = {
    type: 'email' | 'nickname' | 'password'
    initialValue?: string
}

type AuthInputReturns = {
    value: string
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    isPass: boolean
}

function useAuthInput({
    type,
    initialValue = '',
}: AuthInputProps): AuthInputReturns {
    const [value, setValue] = useState<string>(initialValue)
    const [isPass, setIsPass] = useState(false)
    const debouncedValue = useDebounce(value)

    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setValue(event.target.value)
    }

    useEffect(() => {
        console.log(checkReg(type, debouncedValue))
        setIsPass(checkReg(type, debouncedValue))
    }, [debouncedValue])

    return { value, onChange, isPass }
}

export default useAuthInput
