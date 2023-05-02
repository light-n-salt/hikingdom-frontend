import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './ProfileUpdatePage.module.scss'

import UserImage from 'components/common/UserImage'
import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'

import useAuthInput from 'hooks/useAuthInput'
import useCheckPw from 'hooks/useCheckPw'

//Todo : axios 연결 및 에러메세지 세팅

function ProfileUpdatePage() {
  const { theme } = useContext(ThemeContext)
  const navigate = useNavigate()
  const [nicknameErr, setNicknameErr] = useState('nicknameErr')
  const [pwErr, setPwErr] = useState('pwerr')

  const userInfo = {
    imgUrl:
      'https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80',
    username: '이병호리병',
  }

  // 닉네임 변경
  const {
    value: nickname,
    onChange: changeNickname,
    isPass: isNicknamePass,
    condition: nicknameCond,
  } = useAuthInput({ type: 'nickname' })

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

  return (
    <div className={`page p-sm ${theme} ${styles.profile}`}>
      <div className={`content ${theme} ${styles.img}`}>
        <UserImage imgUrl={userInfo.imgUrl} size="lg" />
      </div>

      <div className={styles.signout}>회원탈퇴</div>

      <div className={`content ${theme} ${styles.nickname}`}>
        <Button
          text="취소"
          color="secondary"
          size="sm"
          onClick={() => navigate('/profile')}
        />

        <span className={styles.err}>{nicknameErr}</span>
        <LabelInput
          label="닉네임"
          value={nickname}
          onChange={changeNickname}
          placeholder={nicknameCond}
        />
        <Button
          text="닉네임 수정"
          color={isNicknamePass ? 'primary' : 'gray'}
        />
      </div>

      <div className={`content ${theme} ${styles.password}`}>
        <span className={styles.err}>{pwErr}</span>
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
          placeholder="새 비밀번호를 확인해주세요"
          type="password"
        />
        <Button
          text="비밀번호 수정"
          color={isPwPass && isNewPwPass && isCheckPwPass ? 'primary' : 'gray'}
        />
      </div>
    </div>
  )
}

export default ProfileUpdatePage
