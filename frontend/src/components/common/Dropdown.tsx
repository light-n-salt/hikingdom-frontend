import React, { useState } from 'react';
import styles from './Dropdown.module.scss';
import { BsFillCaretDownFill } from 'react-icons/bs'

type Option = {
  value: string;
  label: string;
}

type Props = {
  options: Option[];
  defaultLabel?: string;
  onChange?: (value: string) => void;
  isRight? : boolean;
}

function Dropdown({ options, defaultLabel, onChange, isRight = true } : Props) {
  const [label, setLabel] = useState<string>(defaultLabel || options[0].label);
  const [isShow, setIsShow] = useState(false);

  function onClickSelect (option: Option) {
    setIsShow(false)
    const newLabel = option.label;
    const newValue = option.value;
    setLabel(newLabel);
    if (onChange) {
      onChange(newValue);
    }
  }

  function onClickToggleShow() {
    setIsShow((isShow) => !isShow)
  }

  return (
    <div className={`${styles.container} ${styles[isRight ? 'right' : 'left']}`}>
        <div
            className={styles.select}
            onClick={onClickToggleShow}
            >
            {label}
            <div className={`{$styles.arrow} ${styles[isShow ? 'up' : 'down']}`}>
                <BsFillCaretDownFill />
            </div>
        </div>
        {isShow && (
            <div className={styles.options}>
            {options.map((option) => (
                <div className={styles.option} key={option.value} onClick={() => onClickSelect(option)}>
                {option.label}
                </div>
            ))}
            </div>
        )}
    </div>
  )
}

export default Dropdown;