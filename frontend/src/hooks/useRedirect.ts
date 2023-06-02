import { useEffect } from 'react'

import { useNavigate } from 'react-router-dom'

// 인자값이 하나라도 NaN일 때 404로 redirect
// 모두 number일 때 number로 형변환된 인자값을 객체 형식으로 반환
// 인자가 number 형식으로 들어올 시 float은 절대 아닐 것이라고 간주함

function useRedirect(...args: (string | number)[]) {
  const navigate = useNavigate()
  const parsedValues = args.map((arg) => {
    if (typeof arg === 'string') {
      // 인자가 string일 때 (url에서 가져온 경우)
      if (arg.includes('.')) {
        // 소수점을 포함한다면 NaN
        return NaN
      }
    }
    return Number(arg)
  })

  useEffect(() => {
    if (parsedValues.some(isNaN)) {
      navigate('/404')
    }
  }, [navigate, ...parsedValues])

  // 배열을 객체 형식으로 변환
  const parsedObject: { [key: string]: any } = {}
  args.forEach((arg, index) => {
    parsedObject[`arg${index + 1}`] = parsedValues[index]
  })

  return parsedObject
}

export default useRedirect

// parseInt로 하지 않은 이유

// parseInt는 인자 타입이 무조건 string
// parseInt(String(arg)) 이렇게 해야 타입에러가 나지 않는데

// 유저가 url에 직접 입력헤버리는 경우 다음과 같은 문제가 있음
// 1. 문자열을 number로 형변환시 float 형식일 때 ex) 1.1 => 1로 간주
// 2. 문자열의 시작이 숫자인 경우 ex) 1가나다 => 1로 간주
