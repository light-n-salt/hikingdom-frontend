import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './PwUpdateForm.module.scss'

import LabelInput from 'components/common/LabelInput'
import Button from 'components/common/Button'

import useAuthInput from 'hooks/useAuthInput'
import useCheckPw from 'hooks/useCheckPw'

function PwUpdateForm() {
    const { theme } = useContext(ThemeContext)

    const [pwErr, setPwErr] = useState('pwerr')

    const onClickUpdatePw = () => {
        console.log('비밀번호 ')
    }

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
                color={
                    isPwPass && isNewPwPass && isCheckPwPass
                        ? 'primary'
                        : 'gray'
                }
                onClick={onClickUpdatePw}
            />
        </div>
    )
}

export default PwUpdateForm
