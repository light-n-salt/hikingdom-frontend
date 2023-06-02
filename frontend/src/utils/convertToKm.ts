// km 단위 소수점 둘째자리까지 반환
export function convertToKm(value: number): string {
  const formattedValue = (value / 1000).toFixed(2)
  const parts = formattedValue.toString().split('.')
  const result =
    Number(parts[0]).toLocaleString() + (parts[1] ? '.' + parts[1] : '')
  return result
}
