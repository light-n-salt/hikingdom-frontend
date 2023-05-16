import * as THREE from 'three'
import React from 'react'
import { Canvas, useLoader } from '@react-three/fiber'
import { OrbitControls } from '@react-three/drei'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'

type ClubMountainprops = {
  zoom: number
  assetInfo: any[]
}

function ClubMountain({ zoom, assetInfo }: ClubMountainprops) {

  return (
    // 속성으로 camera 설정을 해줄 수도 있지만, 딱히 설정하지 않아도 mesh가 보임
    <Canvas
      camera={{
        zoom: zoom,
        position: [-10, 20, -10],
        fov: 80,
        near: 0.1,
        far: 80,
      }}
    >
        <ambientLight />
        {/* 
				scene에 존재하는 모든 물체에 전역적으로 빛을 비춰줌
				방향이 존재하지 않기 때문에, 그림자를 만들 수 없음
			*/}

        <pointLight position={[10, 10, 10]} />
        {/* 일광과 같이, 아주 먼 거리의 광원에서 평행으로 진행하는 빛 */}

      <group>
        {assetInfo.map((info, index) => (
          <Mesh key={index} position={info.position} url={info.assetUrl} />
        ))}
      </group>

      {/* target: 카메라의 주시점. x, y, z 축 순으로 설정할 수 있다.
          enableDamping: true를 설정할 경우, 드래그 시의 애니메이션을 부드럽게 한다. 디폴트 값은 false. */}
      <OrbitControls enableDamping={true} />
    </Canvas>
  )
}

export default ClubMountain

// Mesh의 postion 타입 정의 (x축, y축, z축)
type MeshProps = {
  position: THREE.Vector3
  url: string
}

// Mesh 생성 함수
function Mesh({ position, url, ...props }: MeshProps) {
  const gltf = useLoader(GLTFLoader, url)

  return (
    <primitive
      // React Three Fiber에서는 각 요소가 고유한 ID를 가져야 중복 가능
      // scene 속성을 가져와 <primitive> 요소의 object 속성에 할당 => 자체 고유한 ID 가능
      // gltf.scene.clone()을 사용하여, 복사된 THREE.Group을 할당
      object={gltf.scene.clone()} // 고유한 ID 생성
      position={position}
    />
  )
}
