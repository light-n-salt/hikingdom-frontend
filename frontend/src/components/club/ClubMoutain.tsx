import * as THREE from 'three'
import React, { useRef, useState } from 'react'
import { Canvas, useFrame, ThreeElements, useLoader } from '@react-three/fiber'
import { Vector3 } from 'three'
import styles from './ClubMountain.module.scss'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'

// Mesh의 postion 타입 정의 (x축, y축, z축)
interface CubeProps {
  position?: Vector3
}

// Mesh 생성 함수
function Cube({ position, ...props }: CubeProps) {
  const gltf = useLoader(
    GLTFLoader,
    'https://lightnsalt.s3.ap-northeast-2.amazonaws.com/asset/flower9.gltf'
  )

  const ref = useRef<THREE.Mesh>(null)

  // 호버, 클릭했을 때 효과
  const [hovered, hover] = useState(false)
  const [clicked, click] = useState(false)

  // useFrame - 애니메이션 효과
  // === (draw -> requestAnimationFrame)
  // useFrame((state, delta) => {
  //   if (ref.current) {
  //     ref.current.rotation.y += delta
  //   }
  // })
  useFrame((state, delta, frame) => {
    const mesh = gltf.scene.children[0]
    // mesh.rotation.y += delta
  })

  return (
    <primitive
      // {...props}
      // ref={ref}
      object={gltf.scene}
      position={position}
      scale={clicked ? 1.5 : 1}
      onClick={() => click(!clicked)}
      onPointerOver={() => hover(true)}
      onPointerOut={() => hover(false)}
    >
      {/* <boxGeometry args={[1, 1, 1]} /> */}
      {/* <meshStandardMaterial/> */}
    </primitive>
  )
}

function ClubMountain() {
  return (
    // 속성으로 camera 설정을 해줄 수도 있지만, 딱히 설정하지 않아도 mesh가 보임
    <Canvas>
      <ambientLight />
      {/* 
				scene에 존재하는 모든 물체에 전역적으로 빛을 비춰줌
				방향이 존재하지 않기 때문에, 그림자를 만들 수 없음
			*/}

      <pointLight position={[10, 10, 10]} />
      {/* 일광과 같이, 아주 먼 거리의 광원에서 평행으로 진행하는 빛 */}

      <Cube position={new Vector3(0, 0, 0)} />
      <Cube position={new Vector3(0, 0, -10)} />
    </Canvas>
  )
}

export default ClubMountain
