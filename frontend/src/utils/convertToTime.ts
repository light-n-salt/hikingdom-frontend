// km 단위 소수점 둘째자리까지 반환
export function convertToTime(minutes: number) {
  let hours: number

  if (minutes > 60) {
    hours = Math.floor(minutes / 60)
    minutes = Math.floor(minutes % 60)

    return  minutes >= 10 ? `${hours}:${minutes}` : `${hours}:0${minutes}`
  } else {
    return minutes >= 10 ? `00:${minutes}` : `00:0${minutes}`
  }
}

export function convertToHour(minutes: number) {
  let hours: number

  if (minutes > 60) {
    hours = Math.floor(minutes / 60)
    minutes = Math.floor(minutes % 60)
    return minutes ? `${hours}시간 ${minutes}분` : `${hours}시간`
  } else {
    return `${minutes}분`
  }
}
