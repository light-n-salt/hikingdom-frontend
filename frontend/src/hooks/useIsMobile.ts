/*
실행환경의 모바일 여부를 판단하는 커스텀 훅
*/

function useIsMobile(): boolean {
  const details = navigator.userAgent // 현재 디바이스에 대한 정보를 가져온다

  // 아래 문자가 details에 포함되어 있으면 모바일 기기이다.
  // 참고자료 1. https://developer.mozilla.org/en-US/docs/Web/HTTP/Browser_detection_using_the_user_agent#mobile_tablet_or_desktop
  // 참고자료 2.https://www.tutorialspoint.com/javascript-how-to-detect-if-a-website-is-open-on-a-mobile-or-a-desktop
  const regexp = /Mobi|android|iphone|kindle|ipad/

  const isMobile = regexp.test(details) // 정규식 검사로 모바일 여부 판단

  return isMobile
}

export default useIsMobile
