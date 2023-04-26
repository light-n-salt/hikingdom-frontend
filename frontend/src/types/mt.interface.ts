// 산 검색
// 오늘의 산
export interface MtInfo {
    mountainId: number
    name: string
    maxAlt: number
    address: string
    imgUrl: string
}

// 산 상세정보
export interface MtInfoDetail extends MtInfo {
    timeDuration: number
    hikingNumber: number
    peaks: string[]
    description: string
    asset: string
}
