// km 단위 소수점 둘째자리까지 반환
export function convertToTime(minutes: number) {
  let hours: number

  if (minutes > 60) {
    hours = Math.floor(minutes / 60)
    minutes = Math.floor(minutes % 60)

    return `${hours}:${minutes}`
  } else {
    return `00:${minutes}`
  }
}
