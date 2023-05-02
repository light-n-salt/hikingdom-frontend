import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import useAuthInput from 'hooks/useAuthInput'
import React from 'react'
import { useNavigate } from 'react-router-dom'
import sytles from './LoginForm.module.scss'
import services from 'apis/services'

function LoginForm() {
    const navigate = useNavigate()

    const {
        value: email,
        onChange: changeEmail,
        isPass: isEmailPass,
    } = useAuthInput({ type: 'email' })

    const {
        value: password,
        onChange: changePw,
        isPass: isPwPass,
    } = useAuthInput({ type: 'password' })

    function login() {
        if (!isEmailPass || !isPwPass) return
        services
            .login(email, password)
            .then((res) => {
                navigate('/main')
            })
            .catch(() => {})
    }
    function toAgree() {
        navigate('/agreement')
    }

    return (
        <div className={sytles.container}>
            <div className={sytles.inputs}>
                <LabelInput
                    label="이메일"
                    value={email}
                    onChange={changeEmail}
                    isPass={isEmailPass}
                    placeholder="이메일을 입력해주세요"
                />
                <LabelInput
                    label="비밀번호"
                    value={password}
                    onChange={changePw}
                    isPass={isPwPass}
                    placeholder="비밀번호를 입력해주세요"
                    type="password"
                />
                비밀번호 찾기
            </div>
            <div className={sytles.buttons}>
                <Button
                    text="로그인"
                    color="primary"
                    size="lg"
                    onClick={login}
                />
                <Button
                    text="회원가입"
                    color="white"
                    size="lg"
                    onClick={toAgree}
                />
            </div>
        </div>
    )
}

export default LoginForm
