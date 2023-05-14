function sendRefreshToken(refreshToken: string) {
  console.log(2)
  console.log(refreshToken)
  // refreshToken을 받아 처리하는 로직을 구현합니다.
  localStorage.setItem('refreshToken', refreshToken)
}

export default sendRefreshToken
