export function untilMidnight(): number {
  // Get the current date
  const currentDate = new Date()

  // Create a new Date object for midnight tonight
  const midnight = new Date()
  midnight.setHours(24)
  midnight.setMinutes(0)
  midnight.setSeconds(0)
  midnight.setMilliseconds(0)

  // Calculate the difference in seconds between the two dates
  const remainingSeconds = Math.floor(
    (midnight.getTime() - currentDate.getTime()) / 1000
  )

  return remainingSeconds * 1000
}
