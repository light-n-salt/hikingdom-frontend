export default function checkReg(
    type: 'email' | 'nickname' | 'password',
    value: string
): boolean {
    const pattern = {
        email: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
        nickname: /[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,16}/, // 2~16자 한글, 영문 대소문자, 숫자
        password:
            /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/, // 8~16 자, 문자, 숫자, 특수 문자 포함
    }[type]

    return pattern.test(value)
}
