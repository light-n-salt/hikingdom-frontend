import React, { useContext } from 'react'
import { ThemeContext } from 'styles/ThemeProvider'
import PageHeader from 'components/common/PageHeader'
import MtDetail from 'components/main/MtDetail'
import { MtInfoDetail } from 'types/mt.interface'
import styles from './MtDetailPage.module.scss'

function MtDetailPage() {
  // const { theme } = useContext(ThemeContext)

  return (
    <>
      <div className={`page p-sm ${styles.detail}`}>
        <img src={mtInfo.imgUrl} className={styles.image} />
        <PageHeader title="" url="/main/search" color="light" />
        <MtDetail mtInfo={mtInfo} />
      </div>
    </>
  )
}

export default MtDetailPage

const mtInfo: MtInfoDetail = {
  mountainId: 1,
  name: '관악산',
  maxAlt: 608,
  address: '경기 과천시 중앙동',
  imgUrl:
    'https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/8sX5/image/u8B0YI9f8TCYpaL_b4Q4_DTujco.jpg',
  timeDuration: 180,
  peaks: ['연주대'],
  description:
    '관악산은 예로부터 정기가 흘러, 반만년 역사 깊은 한양 성터에 찬란히 빛나도다 우리학교는. 이 강산 축복받은 배움집이요 지인용 연마하여 자라는도다. 해달이 무궁토록 하리라. 관악산은 예로부터 정기가 흘러, 반만년 역사 깊은 한양 성터에 찬란히 빛나도다 우리학교는. 이 강산 축복받은 배움집이요 지인용 연마하여 자라는도다.',
  transport: '정부과천청사역, 과천역, 사당역, 서울대입구역',
  facility: '주차장, 코스 내 화장실',
  asset: [
    {
      assetId: 1,
      name: '관악산 나무',
      assetUrl:
        'https://img.freepik.com/premium-vector/abstract-tree-3d-vector-icon-cartoon-minimal-style_365941-704.jpg',
      getCondition: '관악산을 완등했을 때, 받을 수 있습니다.',
    },
  ],
}
