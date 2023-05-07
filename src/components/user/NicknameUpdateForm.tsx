import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { useMutation, QueryClient } from '@tanstack/react-query'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './NicknameUpdateForm.module.scss'

import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'
import toast from 'components/common/Toast'
import useAuthInput from 'hooks/useAuthInput'

import { updateNickname } from 'apis/services/users'

function NicknameUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()

  // 닉네임 수정
  const {
    value: nickname,
    onChange: changeNickname,
    isPass: isNicknamePass,
    condition: nicknameCond,
  } = useAuthInput({ type: 'nickname' })

  // invalidate 할 때 사용하는 함수
  const queryClient = new QueryClient()

  // 닉네임 수정 함수
  const onClickUpdate = useMutation(() => updateNickname(nickname), {
    onSuccess: () => {
      toast.addMessage('success', '닉네임이 변경되었습니다')
      queryClient.invalidateQueries(['user'])
      navigate('/profile')
    },
    onError: () => {
      toast.addMessage('error', '사용할 수 없는 닉네임입니다')
    },
  })

  return (
    <div className={`content ${theme} ${styles.nickname}`}>
      <Button
        text="취소"
        color="secondary"
        size="sm"
        onClick={() => navigate('/profile')}
      />

      <LabelInput
        label="닉네임"
        value={nickname}
        onChange={changeNickname}
        placeholder={nicknameCond}
      />
      <Button
        text="닉네임 수정"
        color={isNicknamePass ? 'primary' : 'gray'}
        onClick={() => onClickUpdate.mutate()}
      />
    </div>
  )
}

export default NicknameUpdateForm
