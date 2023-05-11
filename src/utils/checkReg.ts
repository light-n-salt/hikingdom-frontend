// 정규식 모음 객체
const REG_EXP = {
  email: {
    pattern: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
    condition: 'hiking@example.com',
  },
  nickname: {
    pattern: /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,16}$/,
    condition: '2~16자 한글, 영어, 숫자 조합',
  },
  password: {
    pattern:
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/,
    condition: '8~16자, 영어, 숫자, 특수문자 포함',
  },
  code: {
    pattern: /^[0-9]{6}$/,
    condition: '6자리 숫자',
  },
  name: {
    pattern: /[ㄱ-ㅎ|가-힣|\w+|a-z|A-Z|0-9|]{1,20}/,
    condition: '20자 이내',
  },
}

type checkExpReturns = {
  result: boolean // 정규식 테스트 결과
  condition: string // 정규식 조건
}

// 정규식 타입과, 테스트할 값을 전달 받음
export default function checkReg(
  type: 'email' | 'nickname' | 'password' | 'code',
  value: string
): checkExpReturns {
  const { pattern, condition } = REG_EXP[type]
  const result = pattern.test(value) //정규식 테스트

  return { result, condition }
}
