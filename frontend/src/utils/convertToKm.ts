// km 단위 소수점 둘째자리까지 반환
export function convertToKm(value: number) {
    return (value / 1000).toFixed(2)
}
