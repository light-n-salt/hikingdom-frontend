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
  return (
    <div className={styles.detail}>
      <MtTitle
        name={mtInfo.name}
        maxAlt={mtInfo.maxAlt}
        timeDuration={mtInfo.totalDuration}
        // assetUrl={mtInfo.asset[0].assetUrl}
        assetUrl="https://img.freepik.com/premium-vector/abstract-tree-3d-vector-icon-cartoon-minimal-style_365941-704.jpg"
      />
      <MtContent
        address={mtInfo.address}
        peaks={mtInfo.peaks}
        transport={mtInfo.transport}
        description={mtInfo.description}
      />
    </div>
  )
}

export default MtDetail
