import React, { useState, useRef } from 'react'

import styles from './SignupForm.module.scss'

import { useNavigate } from 'react-router-dom'

import {
  useCheckEmail,
  useConfirmEmail,
  useCheckNicknameQuery,
  useSignUp,
} from 'apis/services/users'
import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import Loading from 'components/common/Loading'
import toast from 'components/common/Toast'
import useAuthInput from 'hooks/useAuthInput'
import useCheckPw from 'hooks/useCheckPw'

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

  // ref
  const passwordRef = useRef<HTMLInputElement>(null)
  const checkPasswordRef = useRef<HTMLInputElement>(null)

  // 0: 통과
  // 1: default
  // 2: 통과 못함
  const [isAuthStatus, setIsAuthStatus] = useState(1) // 이메일 인증 여부 판단
  const [isDupStatus, setIsDupStatus] = useState(1) // 닉네임 중복 여부 판단

  // 이메일 인증코드 요청
  const { mutate: checkEmail, isLoading: checkEmailLoading } =
    useCheckEmail(email)

  function onClickCheckEmail(event: any) {
    event.preventDefault()
    checkEmail()
  }

  // 이메일 인증코드 확인
  const { mutateAsync: confrimEmail } = useConfirmEmail(email, code)

  function onClickConfirmEmail(event: any) {
    event.preventDefault()
    confrimEmail()
      .then((res) => {
        setIsAuthStatus(0)
      })
      .catch((err) => {
        setIsAuthStatus(2)
      })
  }

  // 닉네임 중복 확인
  const { refetch: checkNickname } = useCheckNicknameQuery(nickname)

  function onClickCheckNickname(event: any) {
    event.preventDefault()
    checkNickname({ throwOnError: true })
      .then((res) => {
        setIsDupStatus(0)
        toast.addMessage('success', res.data!.message)
      })
      .catch((err) => {
        setIsDupStatus(2)
        toast.addMessage('error', err.data.message)
      })
  }

  // 회원가입 api 요청
  const { mutate: signUp } = useSignUp(email, nickname, password, checkPassword)

  function onClickSignUp() {
    // 모든 조건을 충족한 경우에만 api 요청
    if (isAuthStatus || isDupStatus || !isPwPass || !isCheckPwPass)
      return toast.addMessage('success', '모든 정보를 올바르게 기입해주세요')
    signUp()
  }

  return (
    <div className={styles.container}>
      {checkEmailLoading && (
        <div className={styles.loading}>
          <Loading type="circle" />
        </div>
      )}
      <form className={styles.form}>
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
            onClick={onClickCheckEmail}
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
            onClick={onClickConfirmEmail}
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
            onClick={onClickCheckNickname}
          />
        </div>
        <LabelInput
          inputRef={passwordRef}
          label="비밀번호"
          value={password}
          onChange={changePw}
          onKeyDown={(event: React.KeyboardEvent) => {
            if (event.key === 'Enter') {
              event?.preventDefault()
              checkPasswordRef.current && checkPasswordRef.current.focus()
            }
          }}
          isPass={isPwPass}
          placeholder={pwCond}
          type="password"
        />
        <LabelInput
          inputRef={checkPasswordRef}
          label="비밀번호 확인"
          value={checkPassword}
          onChange={changeCheckPw}
          onKeyDown={(event: React.KeyboardEvent) => {
            if (event.key === 'Enter') {
              event?.preventDefault()
              onClickSignUp()
            }
          }}
          isPass={isCheckPwPass}
          placeholder="비밀번호를 확인해주세요"
          type="password"
        />
      </form>
      <Button text="회원가입" color="primary" onClick={onClickSignUp} />
    </div>
  )
}

export default SignupForm
