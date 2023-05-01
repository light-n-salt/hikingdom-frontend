import React from 'react'
import styles from './PastMeetupList.module.scss'

import PastMeetupItem from './PastMeetupItem'

import { UserHiking } from 'types/user.interface'

export default function PastMeetupList({
    hikingList,
}: {
    hikingList: UserHiking[]
}) {
    return (
        <div className={`${styles['meetup-list']}`}>
            {hikingList.map((hiking) => (
                <PastMeetupItem key={hiking.hikingRecordId} hiking={hiking} />
            ))}
        </div>
    )
}
