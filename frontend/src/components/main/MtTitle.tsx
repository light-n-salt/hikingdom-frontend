import React, { useContext, useState, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtTitle.module.scss'
import { convertToHour } from 'utils/convertToTime'

import Loading from 'components/common/Loading'
import Modal from 'components/common/Modal'
import AssetModal from './AssetModal'

import { MtAssetInfo } from 'types/mt.interface'
import thousandSeparator from 'utils/thousandSeparator'

import * as THREE from 'three'
import { Canvas } from '@react-three/fiber'
import Asset from './Asset'

type MtTitleProps = {
  name: string
  maxAlt: number
  timeDuration: number
  checkPeak: string
  assets: MtAssetInfo[]
}

function MtTitle({
  name,
  maxAlt,
  timeDuration,
  assets,
  checkPeak,
}: MtTitleProps) {
  const { theme } = useContext(ThemeContext)
  const [isOpen, setIsOpen] = useState(false)

  // 에셋 CSS
  const assetStyle = isOpen ? styles.open : styles.close

  // 캔버스 CSS : inline-style만 가능
  const canvasStyle = isOpen
    ? { width: 'fit-content', height: '30vh', zIndex: 999 }
    : { height: '16vh', zIndex: 0 }

  return (
    <div className={`content ${theme} ${styles.mttitle}`}>
      {/* 모달 */}
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <AssetModal
            name={assets[0].name}
            getCondition={assets[0].getCondition}
            checkPeak={checkPeak}
            onClick={() => setIsOpen(false)}
          />
        </Modal>
      )}
      {/* asset */}
      <div className={`${styles.asset} ${assetStyle}`}>
        {assets.length ? (
          <Canvas style={canvasStyle}>
            <ambientLight />
            <Asset
              position={new THREE.Vector3(0, -0.8, 3)}
              url={assets[0].assetUrl}
              onClick={() => setIsOpen(true)}
            />
          </Canvas>
        ) : (
          <Loading />
        )}
      </div>
      <div className={styles.title}>{name}</div>
      <div className={styles.container}>
        {/* 높이 */}
        <div className={styles.content}>
          <span className={styles.text}>높이</span>
          <span className={styles.bold}>{thousandSeparator(maxAlt)}m</span>
        </div>

        {/* 왕복시간 */}
        <div className={styles.content}>
          <span className={styles.text}>왕복</span>
          <span className={styles.bold}>약 {convertToHour(timeDuration)}</span>
        </div>
        <div className={styles.content}></div>
      </div>
    </div>
  )
}

export default MtTitle
