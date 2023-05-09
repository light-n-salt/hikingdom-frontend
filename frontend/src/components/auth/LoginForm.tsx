import React from 'react'
import sytles from './LoginForm.module.scss'
import { useNavigate } from 'react-router-dom'
import { login, getUserInfo } from 'apis/services/users'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import TextButton from 'components/common/TextButton'
import useAuthInput from 'hooks/useAuthInput'
import { useSetRecoilState } from 'recoil'
import { userInfoState } from 'recoil/atoms'

function LoginForm() {
  const navigate = useNavigate()
  const setUserInfo = useSetRecoilState(userInfoState)

  const {
    value: email,
    onChange: changeEmail,
    isPass: isEmailPass,
  } = useAuthInput({ type: 'email' }) // 사용자 인증 input 커스텀 훅
  const {
    value: password,
    onChange: changePw,
    isPass: isPwPass,
  } = useAuthInput({ type: 'password' }) // 사용자 인증 input 커스텀 훅

  // 로그인 api 요청
  function onClickLogin() {
    // 이메일 형식이 맞고, 비밀번호가 입력된 경우에만 요청을 보냄
    if (!isEmailPass || !password) {
      toast.addMessage('error', `이메일과 비밀번호를 정확하게 입력해주세요`)
      return
    }
    login(email, password)
      .then(() => {
        getUserInfo(setUserInfo)
        navigate('/main')
      })
      .catch((err) => {
        toast.addMessage('error', err.data.message)
      })
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
        <TextButton
          text="비밀번호 찾기"
          color="tertiary"
          onClick={() => navigate('/password')}
        />
      </div>
      <div className={sytles.buttons}>
        <Button
          text="로그인"
          color="primary"
          size="lg"
          onClick={onClickLogin}
        />
        <Button
          text="회원가입"
          color="white"
          size="lg"
          onClick={() => navigate('/agreement')}
        />
      </div>
    </div>
  )
}

export default LoginForm
