import { useState, useEffect, RefObject } from 'react'

interface useInfiniteScrollReturns {
  isLoading: boolean
}

function useInfiniteScroll(
  ref: RefObject<HTMLElement>,
  loadMore: () => Promise<void>,
  isEnd = false,
  threshold = 0
): useInfiniteScrollReturns {
  const [isLoading, setIsLoading] = useState(false)

  const handleScroll = async () => {
    const element = ref.current

    if (isLoading || isEnd || !element) return

    const { scrollTop, scrollHeight, clientHeight } = element

    if (scrollHeight - scrollTop - clientHeight > threshold) return

    setIsLoading(true)
    await loadMore()
    setIsLoading(false)
  }

  // 렌더링 된 다음에
  useEffect(() => {
    const element = ref.current

    if (!element) return

    element.addEventListener('scroll', handleScroll)
    return () => {
      element.removeEventListener('scroll', handleScroll)
    }
  }, [ref, handleScroll])

  return { isLoading }
}

export default useInfiniteScroll
