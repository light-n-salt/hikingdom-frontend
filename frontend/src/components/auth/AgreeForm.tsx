import LabelTextArea from 'components/common/LabelTextArea'
import React, { useState } from 'react'
import styles from './AgreeForm.module.scss'
import Button from 'components/common/Button'
import { useNavigate } from 'react-router-dom'
import { usage, personalInfo } from 'constants/agreements'
import PageHeader from 'components/common/PageHeader'
import CheckBox from 'components/common/CheckBox'
import toast from 'components/common/Toast'

function AgreeForm() {
  const navigate = useNavigate()

  const [isAgreeUsage, setIsAgreeUsage] = useState(false)
  const [isAgreeInfo, setIsAgreeInfo] = useState(false)

  function onClickNext() {
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
          value={usage}
          size="lg"
          placeholder=""
          disabled={true}
        />
        <div className={styles.checkbox}>
          <CheckBox
            id="agree-usage"
            label="동의"
            isChecked={isAgreeUsage}
            onClick={() => {
              setIsAgreeUsage(true)
            }}
          />
          <CheckBox
            id="disagree-usage"
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
          value={personalInfo}
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
