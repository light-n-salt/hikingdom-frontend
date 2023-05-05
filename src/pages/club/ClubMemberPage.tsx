import React, { useEffect } from 'react'

import styles from './ClubMemberPage.module.scss'
import MemberList from 'components/club/MemberList'
import { ClubMemberList } from 'types/club.interface'

const clubMemberList: ClubMemberList = {
  request: [
    {
      "memberId": 1,
      "nickname": "정예지렁이비가오네?",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 1,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
    {
      "memberId": 2,
      "nickname": "이병호루라기",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 2,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
    {
      "memberId": 3,
      "nickname": "조혜진",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 3,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
  ],
  member: [
    {
      "memberId": 1,
      "nickname": "정예지렁이비가오네?",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 1,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
    {
      "memberId": 2,
      "nickname": "이병호루라기",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 2,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
    {
      "memberId": 3,
      "nickname": "조혜진",
      "profileUrl": "https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp",
      "level": 3,
      "totalHikingCount": 3,
      "totalDuration": 800,
      "totalDistance": 730,
    },
  ]
}


function ClubMemberPage() {
  return (
    <div className={styles.page}>
      {clubMemberList.request && <MemberList title="가입대기" length={clubMemberList.request.length} memberList={clubMemberList.request} isButton={true} />}
      <MemberList title="모임 멤버" length={clubMemberList.member.length} memberList={clubMemberList.member}/>
    </div>
  )
}

export default ClubMemberPage
