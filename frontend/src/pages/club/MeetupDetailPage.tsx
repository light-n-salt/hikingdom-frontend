import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import styles from './MeetupDetailPage.module.scss'

import Button from 'components/common/Button'
import PageHeader from 'components/common/PageHeader'
import MeetupDetail from 'components/meetup/MeetupDetail'
import MeetupIntroduction from 'components/meetup/MeetupIntroduction'
import MeetupMembers from 'components/meetup/MeetupMembers'
import MeetupAlbum from 'components/meetup/MeetupAlbum'
import MeetupReviewList from 'components/meetup/MeetupReviewList'
import TextSendBar from 'components/common/TextSendBar'

import { meetupInfoDetail } from 'types/meetup.interface'

function MeetupDetailPage() {
  const { theme } = useContext(ThemeContext)

  const info: meetupInfoDetail = {
    meetupHostId: 0,
    meetupId: 0,
    meetupName: 'string',
    mountainName: 'string',
    startDate: 'string',
    startTime: 'string',
    description: 'string',
    totalMmember: 10,
    isJoin: true,
    memberInfo: [
      {
        memberId: 0,
        profileUrl:
          'https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp',
      },
      {
        memberId: 1,
        profileUrl:
          'https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp',
      },
      {
        memberId: 2,
        profileUrl:
          'https://i.namu.wiki/i/ZeGGZBUYHmppb5RTym8vJmnQulv-UhJygvrUMH_qXurQ08oZzRshu2sToDMet1NFeq4iwnkqY69Y_pKH4C2lmg.webp',
      },
      {
        memberId: 3,
        profileUrl:
          'https://i.namu.wiki/i/IMJTGYaO0v6OYa5iro8bLfHuxdiXflvRQ4BdsMVOEvhFP2VBF74QAdMc4PFs-dJcYu-b9aRFeEnajUO1nDQeDg.webp',
      },
      {
        memberId: 4,
        profileUrl:
          'https://i.namu.wiki/i/IMJTGYaO0v6OYa5iro8bLfHuxdiXflvRQ4BdsMVOEvhFP2VBF74QAdMc4PFs-dJcYu-b9aRFeEnajUO1nDQeDg.webp',
      },
      {
        memberId: 5,
        profileUrl:
          'https://i.namu.wiki/i/IMJTGYaO0v6OYa5iro8bLfHuxdiXflvRQ4BdsMVOEvhFP2VBF74QAdMc4PFs-dJcYu-b9aRFeEnajUO1nDQeDg.webp',
      },
    ],
    photoInfo: [
      {
        photoId: 0,
        memberId: 0,
        imgUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        createdAt: 'YYYY:MM:DD HH:mm:ss',
      },
      {
        photoId: 1,
        memberId: 1,
        imgUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        createdAt: 'YYYY:MM:DD HH:mm:ss',
      },
      {
        photoId: 2,
        memberId: 2,
        imgUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        createdAt: 'YYYY:MM:DD HH:mm:ss',
      },
      // {
      //   photoId: 3,
      //   memberId: 3,
      //   imgUrl:
      //     'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
      //   createdAt: 'YYYY:MM:DD HH:mm:ss',
      // },
    ],
    reviewInfo: [
      {
        memberId: 0,
        nickname: '조혜진진자라',
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        reviewId: 0,
        content:
          '짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요짱짱너무재밌어요',
      },
      {
        memberId: 1,
        nickname: '정예지렁이',
        profileUrl:
          'https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg',
        reviewId: 1,
        content: '짱짱',
      },
    ],
  }

  return (
    <div className={`page p-sm ${theme} ${styles.page}`}>
      <Button text="수정" color="secondary" size="xs" />
      <PageHeader title="일정 제목" url="/club/meetup" color="primary" />
      <MeetupDetail mountain="도봉산" date="2023.04.30" time="14:00" />
      <div className={`page ${theme} ${styles.content}`}>
        <div className={styles.intro}>
          <MeetupIntroduction content={'마리아~ 산타마리아'} />
        </div>
        <MeetupMembers memberInfo={info.memberInfo} />
        <MeetupAlbum photoInfo={info.photoInfo} />
        <MeetupReviewList reviewInfo={info.reviewInfo} />
      </div>
      <TextSendBar placeholder="후기를 입력해주세요" />
    </div>
  )
}

export default MeetupDetailPage
