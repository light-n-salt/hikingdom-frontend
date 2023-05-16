import React, { useRef, useState } from 'react'
import styles from './Asset.module.scss'
import * as THREE from 'three'
import { useLoader, useFrame } from '@react-three/fiber'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import { useSpring, animated } from '@react-spring/three'

interface AssetProps {
  onClick: () => void // 닫는 함수
  url: string
  position?: THREE.Vector3
}

function Asset({ onClick, position, url }: AssetProps) {
  const asset = useLoader(GLTFLoader, url)
  const [click, setClick] = useState(false)
  const scaleRef = useRef(1)

  // 중심 기준으로 회전
  useFrame((state, delta) => {
    asset.scene.rotation.y += delta
  })

  const onClickZoom = () => {
    onClick()
    setClick(true)
  }

  // 클릭시 크기 커짐
  // useFrame(() => {
  //   if (click && scaleRef.current < 1) {
  //     scaleRef.current += 0.01
  //   } else if (!click && scaleRef.current > 1) {
  //     scaleRef.current -= 0.01
  //   }
  //   asset.scene.scale.set(scaleRef.current, scaleRef.current, scaleRef.current)
  // })

  return (
    <primitive object={asset.scene} position={position} onClick={onClickZoom} />
  )
}

export default Asset
