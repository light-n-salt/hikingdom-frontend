import React, { useContext, useState } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './SelectBox.module.scss'

import { BsFillCaretDownFill, BsFillCaretUpFill } from 'react-icons/bs'

type SelectBoxProps = {
    placeholder: string
    options: string[] // 옵션 리스트
}

function SelectBox({ placeholder, options }: SelectBoxProps) {
    const { theme } = useContext(ThemeContext)

    const [selected, setSelected] = useState(placeholder) // 선택된 옵션
    const [isShow, setIsShow] = useState(false) // 옵션 리스트 표시

    const onClickSelect = (index: number) => {
        setSelected(options[index])
    }

    return (
        <div className={`${styles.selectBox} ${styles[theme]}`}>
            <div className={styles.selected}>
                {selected}

                {/* 화살표 버튼 토글 */}
                {isShow ? (
                    <BsFillCaretUpFill onClick={() => setIsShow(!isShow)} />
                ) : (
                    <BsFillCaretDownFill onClick={() => setIsShow(!isShow)} />
                )}
            </div>

            {/* 옵션 리스트 */}
            {isShow && (
                <div className={styles.options}>
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
