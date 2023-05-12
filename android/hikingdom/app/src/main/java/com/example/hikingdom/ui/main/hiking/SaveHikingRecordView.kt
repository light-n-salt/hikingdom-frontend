package com.example.hikingdom.ui.main.hiking

interface SaveHikingRecordView {    // 등산기록 저장 api 통신이 성공되었을때, 실패했을때, 로딩될때의 함수를 구현하기 위한 인터페이스
    fun onSaveHikingRecordSuccess(message: String) // 등산기록 저장 api 통신이 성공되었을때
    fun onSaveHikingRecordFailure(message: String) // 등산기록 저장 api 통신이 실패했을때
}