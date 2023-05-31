import * as THREE from 'three'

export function getPosition(arr: any[]) {
  let sx = -2.5
  let sz = -2.5
  const n = arr.length

  let k = 0
  let m = 5
  let idx = 1

  arr[0].position = new THREE.Vector3(0, 0, 0)

  while (idx < n) {
    for (const [dx, dz] of [
      [1, 0],
      [0, 1],
      [-1, 0],
      [0, -1],
    ]) {
      for (let i = 0; i < m; i++) {
        if (idx >= n) {
          return { arr } // arr와 uniqueArr 함께 반환
        }
        sx += dx
        sz += dz
        arr[idx].position = new THREE.Vector3(sx, 0, sz) // 배열의 각 요소에 위치 정보를 할당

        idx += 1
      }
      if (k % 2 === 0) {
        if (k !== 0) {
          m += 1
        }
      }
      k += 1
    }
  }
  return { arr }
}
