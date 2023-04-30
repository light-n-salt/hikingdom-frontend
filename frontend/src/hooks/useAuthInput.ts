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
    isError: boolean
}

function useAuthInput({
    type,
    initialValue = '',
}: AuthInputProps): AuthInputReturns {
    const [value, setValue] = useState<string>(initialValue)
    const [isError, setIsError] = useState(false)
    const debouncedValue = useDebounce(value)

    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setValue(event.target.value)
    }

    useEffect(() => {
        setIsError(checkReg(type, debouncedValue))
    }, [debouncedValue])

    return { value, onChange, isError }
}

export default useAuthInput
