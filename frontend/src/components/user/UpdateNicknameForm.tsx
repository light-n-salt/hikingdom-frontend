import React, { useContext } from 'react'

import styles from './UpdateNicknameForm.module.scss'

import { useQueryClient } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'


import { useUpdateNickname, useUserInfoQuery } from 'apis/services/users'
import Button from 'components/common/Button'
import ErrorMessage from 'components/common/ErrorMessage'
import LabelInput from 'components/common/LabelInput'
import Loading from 'components/common/Loading'
import toast from 'components/common/Toast'
import useAuthInput from 'hooks/useAuthInput'
import { ThemeContext } from 'styles/ThemeProvider'

function UpdateNicknameForm() {
  const navigate = useNavigate()
  const { theme } = useContext(ThemeContext)
  const queryClient = useQueryClient()

  // 유저 정보
  const { data: userInfo, isLoading, isError } = useUserInfoQuery()

  // 유저 정보를 바탕으로 nikcname 커스텀 훅 호출
  const {
    value: nickname,
    onChange: changeNickname,
    isPass: isNicknamePass,
    condition: nicknameCond,
  } = useAuthInput({ type: 'nickname', initialValue: userInfo?.nickname || '' })

  // 닉네임 수정 함수
  const { mutate: updateNickname } = useUpdateNickname(nickname)

  const onClickUpdate = () => {
    if (!nickname.trim()) {
      toast.addMessage('error', '닉네임을 입력해주세요')
      return
    }
    if (!isNicknamePass) {
      toast.addMessage('error', '닉네임 형식이 맞지 않습니다.')
      return
    }
    if (nickname === userInfo?.nickname) {
      toast.addMessage('error', '현재 닉네임과 동일합니다')
      return
    }
    updateNickname()
  }

  if (isLoading) {
    return <Loading />
  }

  if (isError) {
    return <ErrorMessage message="유저 정보를 불러오는데 실패했습니다" />
  }

  return (
    <div className={`content ${theme} ${styles.nickname}`}>
      <LabelInput
        label="닉네임"
        value={nickname}
        onChange={changeNickname}
        placeholder={nicknameCond}
      />
      <Button
        text="닉네임 수정"
        color={
          isNicknamePass && nickname !== userInfo.nickname ? 'primary' : 'gray'
        }
        onClick={onClickUpdate}
      />
    </div>
  )
}

export default UpdateNicknameForm
