import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtDetail.module.scss'

import MtTitle from './MtTitle'
import MtContent from './MtContent'

import { MtInfoDetail } from 'types/mt.interface'

type MtDetailProps = {
  mtInfo: MtInfoDetail
}

function MtDetail({ mtInfo }: MtDetailProps) {
  const { theme, toggleTheme } = useContext(ThemeContext)
  return (
    <div className={styles.detail}>
      <MtTitle
        name={mtInfo.name}
        maxAlt={mtInfo.maxAlt}
        timeDuration={mtInfo.timeDuration}
        assetUrl={mtInfo.asset[0].assetUrl}
      />
      <MtContent
        address={mtInfo.address}
        peaks={mtInfo.peaks}
        transport={mtInfo.transport}
        facility={mtInfo.facility}
        description={mtInfo.description}
      />
    </div>
  )
}

export default MtDetail
