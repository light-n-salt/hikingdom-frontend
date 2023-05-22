// 산 검색
// 오늘의 산
export interface MtInfo {
  mountainId: number
  name: string
  maxAlt: number
  address: string
  imgUrl: string
}

export interface MtAssetInfo {
  assetId: number
  name: string
  assetUrl: string
  getCondition: string // 에셋 획득 조건
}

// 산 상세정보
export interface MtInfoDetail extends MtInfo {
  totalDuration: number
  peaks: string
  description: string
  checkPeak: string
  assets: MtAssetInfo[]
}
