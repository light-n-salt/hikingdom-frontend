import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './PwUpdateForm.module.scss'
import { useNavigate } from 'react-router-dom'
import { AxiosError } from 'axios'
import { useMutation } from '@tanstack/react-query'

import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'
import toast from 'components/common/Toast'

import useAuthInput from 'hooks/useAuthInput'
import useCheckPw from 'hooks/useCheckPw'

import { updatePw } from 'apis/services/users'

import useUserQuery from 'hooks/useUserQuery'

function PwUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  const { data: userInfo } = useUserQuery()
  // 비밀번호 변경
  const {
    value: password,
    onChange: changePw,
    isPass: isPwPass,
    condition: pwCond,
  } = useAuthInput({ type: 'password' })

  const {
    value: newPassword,
    onChange: changeNewPw,
    isPass: isNewPwPass,
    condition: newPwCond,
  } = useAuthInput({ type: 'password' })

  const {
    value: checkPassword,
    onChange: changeCheckPw,
    isPass: isCheckPwPass,
  } = useCheckPw({ password })

  const onClickUpdate = useMutation(
    () => updatePw(password, newPassword, checkPassword),
    {
      onSuccess: () => {
        toast.addMessage('success', '비밀번호가 변경되었습니다')
        navigate(`/profile/${userInfo?.nickname}`)
      },
      onError: (err: AxiosError) => {
        if (err.status === 401) {
          toast.addMessage('error', '현재 비밀번호가 일치하지 않습니다')
        }
      },
    }
  )

  return (
    <div className={`content ${theme} ${styles.password}`}>
      <LabelInput
        label="현재 비밀번호"
        value={password}
        onChange={changePw}
        isPass={isPwPass}
        placeholder={pwCond}
        type="password"
      />
      <LabelInput
        label="새 비밀번호"
        value={newPassword}
        onChange={changeNewPw}
        isPass={isNewPwPass}
        placeholder={newPwCond}
        type="password"
      />
      <LabelInput
        label="새 비밀번호 확인"
        value={checkPassword}
        onChange={changeCheckPw}
        isPass={isCheckPwPass}
        isError={newPassword !== checkPassword}
        placeholder="새 비밀번호를 확인해주세요"
        type="password"
      />
      <Button
        text="비밀번호 수정"
        color={isPwPass && isNewPwPass && isCheckPwPass ? 'primary' : 'gray'}
        onClick={() => onClickUpdate.mutate()}
      />
    </div>
  )
}

export default PwUpdateForm
