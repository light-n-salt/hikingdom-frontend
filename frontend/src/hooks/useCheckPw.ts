import React, { useState, useEffect } from 'react'
import useDebounce from './useDebounce'

type CheckPwProps = {
    password: string
}

type CheckPwReturns = {
    value: string
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void
    isPass: boolean
}

function useCheckPw({ password }: CheckPwProps): CheckPwReturns {
    const [value, setValue] = useState<string>('')
    const [isPass, setIsPass] = useState(false)
    const debouncedValue = useDebounce(value)

    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setValue(event.target.value)
    }

    useEffect(() => {
        setIsPass(debouncedValue !== '' && password === debouncedValue)
    }, [debouncedValue])

    return { value, onChange, isPass }
}

export default useCheckPw
