import React, { useContext, useState, useEffect } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MtTitle.module.scss'
import { convertToHour } from 'utils/convertToTime'

import Loading from 'components/common/Loading'
import Modal from 'components/common/Modal'
import AssetModal from './AssetModal'

import { MtAssetInfo } from 'types/mt.interface'

import * as THREE from 'three'
import { Canvas } from '@react-three/fiber'
import Asset from './Asset'

type MtTitleProps = {
  name: string
  maxAlt: number
  timeDuration: number
  assets: MtAssetInfo[]
}

function MtTitle({ name, maxAlt, timeDuration, assets }: MtTitleProps) {
  const { theme } = useContext(ThemeContext)
  const [isOpen, setIsOpen] = useState(false)
  // const [pos, setPos] = useState(new THREE.Vector3(0, -0.8, 3))
  const [y, setY] = useState(-0.8)
  const [z, setZ] = useState(3)
  const canvasStyle = isOpen
    ? { width: 'fit-content', height: '40vh', zIndex: 999 }
    : { height: '20vh', zIndex: 0 }

  // const assetPosition = isOpen
  //   ? new THREE.Vector3(0, -0.5, 0.5)
  // : new THREE.Vector3(0, -0.8, 3)

  useEffect(() => {
    if (isOpen) {
      setY(-0.5)
      setZ(0.5)
      // setPos(new THREE.Vector3(0, -0.5, 0.5))
    } else {
      // setPos(new THREE.Vector3(0, -0.8, 3))
      setY(-0.8)
      setZ(3)
    }
  }, [isOpen])

  return (
    <div className={`content ${theme} ${styles.mttitle}`}>
      {/* 모달 */}
      {isOpen && (
        <Modal onClick={() => setIsOpen(false)}>
          <AssetModal
            url={assets[0].assetUrl}
            name={assets[0].name}
            getCondition={assets[0].getCondition}
          />
        </Modal>
      )}
      {/* asset */}
      <div className={styles.asset}>
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
          <span className={styles.bold}>{Math.floor(maxAlt)}m</span>
          <span className={styles.text}>높이</span>
        </div>

        <span className={styles.border}>|</span>

        {/* 왕복시간 */}
        <div className={styles.content}>
          <span className={styles.bold}>약 {convertToHour(timeDuration)}</span>
          <span className={styles.text}>왕복시간</span>
        </div>
        <span className={styles.border}>|</span>
      </div>
    </div>
  )
}

export default MtTitle
