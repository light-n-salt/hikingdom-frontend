import LabelTextArea from 'components/common/LabelTextArea'
import React from 'react'
import styles from './AgreeForm.module.scss'
import Button from 'components/common/Button'
import { useNavigate } from 'react-router-dom'

function AgreeForm() {
    const navigate = useNavigate()

    function toSignup() {
        navigate('/signup')
    }

    return (
        <div className={styles.container}>
            <h1>약관동의</h1>
            <LabelTextArea
                label="이용약관 (필수)"
                value="이거슨 이용야관입니다."
                placeholder=""
                disabled={true}
            />
            <LabelTextArea
                label="개인정보 수집 및 이동 (필수)"
                value="이거슨 개인정보 수집 및 동의내용입니다."
                placeholder=""
                disabled={true}
            />
            <Button
                text="동의 후 가입"
                color="primary"
                size="md"
                onClick={toSignup}
            />
        </div>
    )
}

export default AgreeForm
