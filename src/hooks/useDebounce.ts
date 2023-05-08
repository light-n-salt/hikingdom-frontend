import { useEffect, useState } from 'react'

// value: 입력값, delay: 지연시킬 시간
function useDebounce(value: string, delay?: number): string {
  const [debouncedValue, setDebouncedValue] = useState(value)

  useEffect(() => {
    // 2. delay 후 debouncedValue에 value 값 할당
    const timer = setTimeout(() => setDebouncedValue(value), delay || 300)

    return () => {
      clearTimeout(timer)
    }
  }, [value, delay]) // 1. delay 시간 내에 새로운 value가 들어온다면 다시 delay만큼 지연

  // 3. debouncedValue 반환
  return debouncedValue
}

export default useDebounce
