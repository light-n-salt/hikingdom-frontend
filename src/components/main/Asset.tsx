import React, { useRef, useState, useEffect } from 'react'
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

  // 중심 기준으로 회전
  useFrame((state, delta) => {
    asset.scene.rotation.y += delta
  })

  return (
    <primitive object={asset.scene} position={position} onClick={onClick} />
  )
}

export default Asset
