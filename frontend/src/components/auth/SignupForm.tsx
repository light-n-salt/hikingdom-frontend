import React, { useState } from 'react'
import styles from './SignupForm.module.scss'
import { useNavigate } from 'react-router-dom'
import services from 'apis/services'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import useCheckPw from 'hooks/useCheckPw'
import useAuthInput from 'hooks/useAuthInput'

function SignupForm() {
  const navigate = useNavigate()

  // 사용자 인증 input 커스텀 훅
  const {
    value: email,
    onChange: changeEmail,
    isPass: isEmailPass,
    condition: emailCond,
  } = useAuthInput({ type: 'email' })
  const {
    value: code,
    onChange: changeCode,
    isPass: isCodePass,
    condition: codeCond,
  } = useAuthInput({ type: 'code' })
  const {
    value: nickname,
    onChange: changeNickname,
    isPass: isNicknamePass,
    condition: nicknameCond,
  } = useAuthInput({ type: 'nickname' })
  const {
    value: password,
    onChange: changePw,
    isPass: isPwPass,
    condition: pwCond,
  } = useAuthInput({ type: 'password' })
  const {
    value: checkPassword,
    onChange: changeCheckPw,
    isPass: isCheckPwPass,
  } = useCheckPw({ password })

  const [isAuthStatus, setIsAuthStatus] = useState(1) // 이메일 인증 여부 판단
  const [isDupStatus, setIsDupStatus] = useState(1) // 닉네임 중복 여부 판단

  // 이메일 인증코드 발신 api 요청
  function validEmail() {
    services
      .validEmail(email)
      .then((res) => {
        toast.addMessage('success', res.data.message)
      })
      .catch((err) => {
        toast.addMessage('error', err.data.message)
      })
  }

  // 이메일 인증코드 확인 api 요청
  function confirmEmail() {
    services
      .confirmEmail(email, code)
      .then((res) => {
        setIsAuthStatus(0)
        toast.addMessage('success', res.data.message)
      })
      .catch((err) => {
        setIsAuthStatus(2)
        toast.addMessage('error', err.data.message)
      })
  }

  // 닉네임 중복 api 요청
  function checkNickname() {
    services
      .checkNickname(nickname)
      .then((res) => {
        setIsDupStatus(0)
        toast.addMessage('success', res.data.message)
      })
      .catch((err) => {
        setIsDupStatus(2)
        toast.addMessage('error', err.data.message)
      })
  }

  // 회원가입 api 요청
  function signup() {
    // 모든 조건을 충족한 경우에만 api 요청
    if (isAuthStatus || isDupStatus || !isPwPass || !isCheckPwPass) return
    services
      .signup(email, nickname, password, checkPassword)
      .then(() => {
        toast.addMessage('success', '회원가입에 성공했습니다!')
        navigate('/login')
      })
      .catch((err) => {
        toast.addMessage('error', err.data.message)
      })
  }

  return (
    <div className={styles.container}>
      <div className={styles.flex}>
        <LabelInput
          label="이메일"
          value={email}
          onChange={changeEmail}
          isPass={isAuthStatus === 0}
          isError={isAuthStatus === 2}
          placeholder={emailCond}
          disabled={isAuthStatus === 0}
        />
        <Button
          text="인증"
          color={isEmailPass ? 'primary' : 'gray'}
          size="md"
          onClick={validEmail}
          disabled={isEmailPass ? false : true}
        />
      </div>
      <div className={styles.flex}>
        <LabelInput
          label="인증코드"
          value={code}
          onChange={changeCode}
          isPass={isAuthStatus === 0}
          isError={isAuthStatus === 2}
          placeholder={codeCond}
          disabled={isAuthStatus === 0}
        />
        <Button
          text="확인"
          color={isCodePass ? 'primary' : 'gray'}
          size="md"
          onClick={confirmEmail}
        />
      </div>
      <div className={styles.flex}>
        <LabelInput
          label="닉네임"
          value={nickname}
          onChange={changeNickname}
          isPass={isDupStatus === 0}
          isError={isDupStatus === 2}
          placeholder={nicknameCond}
          disabled={isDupStatus === 0}
        />
        <Button
          text="중복"
          color={isNicknamePass ? 'primary' : 'gray'}
          size="md"
          onClick={checkNickname}
        />
      </div>
      <LabelInput
        label="비밀번호"
        value={password}
        onChange={changePw}
        isPass={isPwPass}
        placeholder={pwCond}
        type="password"
      />
      <LabelInput
        label="비밀번호 확인"
        value={checkPassword}
        onChange={changeCheckPw}
        isPass={isCheckPwPass}
        placeholder="비밀번호를 확인해주세요"
        type="password"
      />
      <Button text="회원가입" color="primary" onClick={signup} />
    </div>
  )
}

export default SignupForm
