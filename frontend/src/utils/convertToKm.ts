// km 단위 소수점 둘째자리까지 반환
export function convertToKm(value: number) {
  const formattedValue = (value / 1000).toFixed(2)
  return formattedValue.replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}
