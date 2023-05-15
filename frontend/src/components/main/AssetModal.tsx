import React from 'react'

type AssetModalProps = {
  url: string
  name: string
  getCondition: string
}

function AssetModal({ url, name, getCondition }: AssetModalProps) {
  return (
    <div>
      <div> {name}</div>
      <div>획득 조건 : {getCondition}</div>
    </div>
  )
}

export default AssetModal
