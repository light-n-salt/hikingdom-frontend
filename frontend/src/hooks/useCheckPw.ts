import React, { useState, useEffect } from 'react'
import useDebounce from './useDebounce'

type CheckPwProps = {
    password: string
}

type CheckPwReturns = {
    value: string
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    isError: boolean
}

function useCheckPw({ password }: CheckPwProps): CheckPwReturns {
    const [value, setValue] = useState<string>('')
    const [isError, setIsError] = useState(false)
    const debouncedValue = useDebounce(value)

    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setValue(event.target.value)
    }

    useEffect(() => {
        setIsError(password !== debouncedValue)
    }, [debouncedValue])

    return { value, onChange, isError }
}

export default useCheckPw
