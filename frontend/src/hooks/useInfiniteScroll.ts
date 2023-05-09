import { useState, useEffect, RefObject } from 'react'

/*
무한스크롤 커스텀 훅
특정 DOM요소를 ref로 받은 뒤,
해당 요소의 마지막으로 스크롤이 닿았을 때, loadMore 함수(api 요청하는 비동기 함수) 실행
*/

type InfiniteScrollProps = {
  ref: RefObject<HTMLElement> // 무한스크롤이 동작할 DOM 엘리먼트를 ref로 받음
  loadMore: () => Promise<any> // 스크롤이 닿았을 시, 동작할 비동기 함수
  isEnd?: boolean // true일 경우 비동기함수 동작하지 않음
  threshold?: number // 스크롤 마진(해당 값 만큼, 미리 스크롤이 닿았다고 판단)
}

type InfiniteScrollReturns = {
  isLoading: boolean // 비동기 요청에 대한 로딩 여부
}

function useInfiniteScroll({
  ref,
  loadMore,
  isEnd = false,
  threshold = 1,
}: InfiniteScrollProps): InfiniteScrollReturns {
  const [isLoading, setIsLoading] = useState(false) // 비동기 요청에 대한 로딩 여부

  // 스크롤 이벤트 감지 함수
  const handleScroll = async () => {
    const element = ref.current // 무한스크롤이 동작할 DOM 엘리먼트
    if (isLoading || isEnd || !element) return // 로딩 중 | 마지막 정보 | element가 null일 경우, 함수 종료

    const { scrollTop, scrollHeight, clientHeight } = element // 엘리먼트의 스크롤 정보
    if (scrollHeight - scrollTop - clientHeight > threshold) return // 스크롤이 끝까지 닿지 않았을 경우, 함수 종료

    setIsLoading(true) // 로딩여부 trued
    await loadMore() // 비동기 요청
    setIsLoading(false) // 로딩여부 false
  }

  useEffect(() => {
    const element = ref.current // 무한스크롤이 동작할 DOM 엘리먼트

    if (!element) return //element가 null일 경우, 함수 종료

    element.addEventListener('scroll', handleScroll) // element에 스크롤 이베트 감지함수 부착
    // cleanup 함수
    return () => {
      element.removeEventListener('scroll', handleScroll)
    }
  }, [ref, handleScroll])

  return { isLoading }
}

export default useInfiniteScroll
