// 프로덕션 모드가 아닐 때만 콘솔에 로그를 출력하는 함수
export default function consoleLog(data: any) {
  if (process.env.NODE_ENV === 'production') return
  console.log(data)
}
