import React from 'react'
import { useParams } from 'react-router'
import TrackingInfo from 'components/user/TrackingInfo'

function TrackingRecordPage() {
  const { hikingRecordId } = useParams() as {
    hikingRecordId: string
    nickname: string
  }
  return <TrackingInfo hikingRecordId={Number(hikingRecordId)} />
}

export default TrackingRecordPage
