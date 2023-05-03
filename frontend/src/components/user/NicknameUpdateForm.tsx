import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './NicknameUpdateForm.module.scss'

import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'

import useAuthInput from 'hooks/useAuthInput'

import { updateNickname } from 'apis/services/users'

function NicknameUpdateForm() {
    const { theme } = useContext(ThemeContext)
    const navigate = useNavigate()

    const [errMsg, setErrMsg] = useState('')

    // 닉네임 수정
    const {
        value: nickname,
        onChange: changeNickname,
        isPass: isNicknamePass,
        condition: nicknameCond,
    } = useAuthInput({ type: 'nickname' })

    // 닉네임 수정 함수
    const onClickUpdateNickname = () => {
        updateNickname(nickname)
            .then(() => {
                navigate('/profile')
                // Todo: Query invalidate 처리
            })
            .catch((err) => {
                if (err.status === 400) {
                    setErrMsg(err.message)
                }
            })
    }

    return (
        <div className={`content ${theme} ${styles.nickname}`}>
            <Button
                text="취소"
                color="secondary"
                size="sm"
                onClick={() => navigate('/profile')}
            />

            <span className={styles.err}>{errMsg}</span>
            <LabelInput
                label="닉네임"
                value={nickname}
                onChange={changeNickname}
                placeholder={nicknameCond}
            />
            <Button
                text="닉네임 수정"
                color={isNicknamePass ? 'primary' : 'gray'}
                onClick={onClickUpdateNickname}
            />
        </div>
    )
}

export default NicknameUpdateForm
