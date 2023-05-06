import React, { useState } from 'react'
import styles from './AgreeForm.module.scss'
import { PERSONAL_AGREEMENT, USAGE_AGREEMENT } from 'constants/agreements'
import { useNavigate } from 'react-router-dom'
import toast from 'components/common/Toast'
import Button from 'components/common/Button'
import CheckBox from 'components/common/CheckBox'
import PageHeader from 'components/common/PageHeader'
import LabelTextArea from 'components/common/LabelTextArea'

function AgreeForm() {
  const navigate = useNavigate()

  const [isAgreeUsage, setIsAgreeUsage] = useState(false) // 이용약관 동의 여부
  const [isAgreeInfo, setIsAgreeInfo] = useState(false) // 개인정보 동의 여부

  function onClickNext() {
    // 모든 약관을 동의한 경우에만, 회원가입 페이지로
    if (!isAgreeUsage || !isAgreeInfo) {
      toast.addMessage('error', `약관을 동의해주세요`)
      return
    }
    navigate('/signup')
  }

  return (
    <div className={styles.container}>
      <PageHeader title="약관동의" url="/login" />
      <div className={styles.form}>
        <LabelTextArea
          label="이용약관 (필수)"
          value={USAGE_AGREEMENT}
          size="lg"
          placeholder=""
          disabled={true}
        />
        <div className={styles.checkbox}>
          <CheckBox
            id="agree-USAGE_AGREEMENT"
            label="동의"
            isChecked={isAgreeUsage}
            onClick={() => {
              setIsAgreeUsage(true)
            }}
          />
          <CheckBox
            id="disagree-USAGE_AGREEMENT"
            label="동의 안함"
            isChecked={!isAgreeUsage}
            onClick={() => {
              setIsAgreeUsage(false)
            }}
          />
        </div>
      </div>
      <div className={styles.form}>
        <LabelTextArea
          label="개인정보 수집 및 이동 (필수)"
          value={PERSONAL_AGREEMENT}
          size="lg"
          placeholder=""
          disabled={true}
        />
        <div className={styles.checkbox}>
          <CheckBox
            id="agree-info"
            label="동의"
            isChecked={isAgreeInfo}
            onClick={() => {
              setIsAgreeInfo(true)
            }}
          />
          <CheckBox
            id="disagree-info"
            label="동의 안함"
            isChecked={!isAgreeInfo}
            onClick={() => {
              setIsAgreeInfo(false)
            }}
          />
        </div>
      </div>
      <Button
        text="동의 후 가입"
        color="primary"
        size="lg"
        onClick={onClickNext}
      />
    </div>
  )
}

export default AgreeForm
