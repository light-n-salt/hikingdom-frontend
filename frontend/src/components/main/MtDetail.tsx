import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtDetail.module.scss'

import MtTitle from './MtTitle'
import MtContent from './MtContent'

function MtDetail() {
    const { theme, toggleTheme } = useContext(ThemeContext)
    return (
        <div>
            MtDetail
            <MtTitle
                name={'관악산'}
                maxAlt={608}
                timeDuration={3}
                hikingNumber={421}
            />
            <MtContent />
        </div>
    )
}

export default MtDetail
