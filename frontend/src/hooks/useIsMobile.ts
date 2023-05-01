import React, { useState, useEffect } from 'react'
import useWindowSize from 'hooks/useWindowSize'

function useIsMobile(): boolean {
    // 커스텀 훅을 사용하여 현재 화면 크기를 가져옴
    const { width } = useWindowSize()

    // 모바일 화면 크기인지 아닌지를 판단하는 변수
    const isMobile = width <= 768

    // 화면 크기 상태를 반환
    return isMobile
}

export default useIsMobile
