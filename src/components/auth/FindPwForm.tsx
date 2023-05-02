import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import useAuthInput from 'hooks/useAuthInput'
import services from 'apis/services'
import React from 'react'
import { useNavigate } from 'react-router-dom'
import styles from './FindPwForm.module.scss'

function FindPwForm() {
  const navigate = useNavigate()

  const {
    value: email,
    onChange: changeEmail,
    isPass: isEmailPass,
  } = useAuthInput({ type: 'email' })

  function findPw() {
    if (!isEmailPass) return
    services
      .findPw(email)
      .then((res) => {})
      .catch(() => {})
  }

  function toLogin() {
    navigate('/login')
  }

  return (
    <div className={styles.container}>
      <h3 className={styles.notice}>
        회원가입한 이메일 주소로 <br />
        초기화된 비밀번호가 전송됩니다
      </h3>
      <LabelInput
        label="이메일"
        value={email}
        onChange={changeEmail}
        isPass={isEmailPass}
        placeholder="이메일을 입력해주세요"
      />
      <div className={styles.buttons}>
        <Button
          text="비밀번호 초기화"
          color="primary"
          size="lg"
          onClick={findPw}
        />
        <Button text="로그인" color="white" size="lg" onClick={toLogin} />
      </div>
    </div>
  )
}

export default FindPwForm
