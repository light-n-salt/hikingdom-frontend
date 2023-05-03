import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SelectBox.module.scss'

import { BsFillCaretDownFill, BsFillCaretUpFill } from 'react-icons/bs'

type SelectBoxProps = {
  placeholder: string
  options: string[] // 옵션 리스트
  isTransparent?: boolean
}

function SelectBox({ placeholder, options, isTransparent }: SelectBoxProps) {
  const { theme } = useContext(ThemeContext)

  const selectBoxStyle = isTransparent ? styles.transparent : styles.selected

  const [selected, setSelected] = useState(placeholder) // 선택된 옵션
  const [isShow, setIsShow] = useState(false) // 옵션 리스트 표시

  // 옵션 리스트 표시
  const onClickShow = () => {
    setIsShow(!isShow)
  }
  // 옵션 선택
  const onClickSelect = (index: number) => {
    setSelected(options[index])
    setIsShow(!isShow)
  }

  return (
    <div className={`${styles['select-box']}`}>
      <div
        className={`content ${theme} ${selectBoxStyle}`}
        onClick={onClickShow}
      >
        {selected}
        {isShow ? <BsFillCaretUpFill /> : <BsFillCaretDownFill />}
      </div>

      {isShow && (
        <div className={`content ${theme} ${styles.options}`}>
          {options.map((option, index) => (
            <li key={index} onClick={() => onClickSelect(index)}>
              {option}
            </li>
          ))}
        </div>
      )}
    </div>
  )
}

export default SelectBox
