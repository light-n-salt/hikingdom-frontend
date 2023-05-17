import * as THREE from 'three'

export function getPosition(arr: any[], filter?: string) {
  let sx = -2.5
  let sz = -2.5
  const n = arr.length

  let k = 1
  let m = 5
  let idx = 1
  const uniqueNameList = new Set() // 중복된 name 값을 제거하기 위한 Set 객체

  arr[0].position = new THREE.Vector3(0, 0, 0)
  uniqueNameList.add('전체')

  while (idx < n) {
    for (const [dx, dz] of [
      [1, 0],
      [0, 1],
      [-1, 0],
      [0, -1],
    ]) {
      if (k % 4 === 0) {
        m += 1
      }

      for (let i = 0; i < m; i++) {
        if (idx >= n) {
          return { arr, uniqueNameList: Array.from(uniqueNameList) } // arr와 uniqueArr 함께 반환
        }
        sx += dx
        sz += dz
        arr[idx].position =
          filter === arr[idx].mountainName
            ? new THREE.Vector3(sx, 1, sz)
            : new THREE.Vector3(sx, 0, sz) // 배열의 각 요소에 위치 정보를 할당
        uniqueNameList.add(arr[idx].mountainName)
        idx += 1
      }
      k += 1
    }
  }
  return { arr, uniqueNameList: Array.from(uniqueNameList) }
}
