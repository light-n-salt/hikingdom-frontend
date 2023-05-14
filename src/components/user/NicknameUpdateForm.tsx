import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './NicknameUpdateForm.module.scss'

import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'
import toast from 'components/common/Toast'
import useAuthInput from 'hooks/useAuthInput'

import { updateNickname } from 'apis/services/users'

import useUserQuery from 'hooks/useUserQuery'

function NicknameUpdateForm() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const { data: userInfo } = useUserQuery()
  const queryClient = useQueryClient()
  // 닉네임 수정
  const {
    value: nickname,
    onChange: changeNickname,
    isPass: isNicknamePass,
    condition: nicknameCond,
  } = useAuthInput({ type: 'nickname' })

  // 닉네임 수정 함수
  const update = useMutation(() => updateNickname(nickname), {
    onSuccess: (res) => {
      toast.addMessage('success', '닉네임이 변경되었습니다')
      queryClient.invalidateQueries(['userProfile'])
      queryClient.invalidateQueries(['user'])
      navigate(`/profile/${nickname}`)
    },
    onError: () => {
      toast.addMessage('error', '이미 존재하는 닉네임입니다.')
    },
  })

  const onClickUpdate = () => {
    if (!nickname.trim()) {
      toast.addMessage('error', '닉네임을 입력해주세요.')
      return
    }

    if (!isNicknamePass) {
      toast.addMessage('error', '닉네임을 형식이 맞지 않습니다.')
      return
    }
    update.mutate()
  }

  return (
    <div className={`content ${theme} ${styles.nickname}`}>
      <Button
        text="취소"
        color="secondary"
        size="sm"
        onClick={() => navigate(`/profile/${userInfo?.nickname}`)}
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
        onClick={onClickUpdate}
      />
    </div>
  )
}

export default NicknameUpdateForm
