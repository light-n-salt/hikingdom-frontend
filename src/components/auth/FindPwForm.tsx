import React from 'react'
import styles from './FindPwForm.module.scss'
import { useNavigate } from 'react-router-dom'
import services from 'apis/services'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import LabelInput from 'components/common/LabelInput'
import useAuthInput from 'hooks/useAuthInput'

function FindPwForm() {
  const navigate = useNavigate()

  const {
    value: email,
    onChange: changeEmail,
    isPass: isEmailPass,
  } = useAuthInput({ type: 'email' }) // 사용자 인증 input 커스텀 훅

  function findPw() {
    // 이메일 형식이 맞은 경우에만 api 요청을 보냄
    if (!isEmailPass) {
      toast.addMessage('error', '이메일을 정확하게 입력해주세요')
      return
    }
    services
      .findPw(email)
      .then(() => {
        toast.addMessage(
          'success',
          '해당 이메일로 새 비밀번호가 전송되었습니다'
        )
      })
      .catch(() => {
        toast.addMessage('error', '해당 이메일에 대한 정보가 없습니다')
      })
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
        <Button
          text="로그인"
          color="white"
          size="lg"
          onClick={() => navigate('/login')}
        />
      </div>
    </div>
  )
}

export default FindPwForm
