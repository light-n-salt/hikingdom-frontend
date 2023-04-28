import React from 'react'
import styles from './LevelModal.module.scss'

import seed from 'assets/level/seed.png'
import sprout from 'assets/level/sprout.png'
import leaf from 'assets/level/leaf.png'
import twig from 'assets/level/twig.png'
import flower from 'assets/level/flower.png'
import fruit from 'assets/level/fruit.png'
import tree from 'assets/level/tree.png'
import forest from 'assets/level/forest.png'
import mountain from 'assets/level/mountain.png'
import god from 'assets/level/god.png'

function LevelModal() {
    return (
        <div className={styles['level-modal']}>
            <LevelItem level={1} imgSrc={seed} des={'씨앗'} />
            <LevelItem level={2} imgSrc={sprout} des={'새싹'} />
            <LevelItem level={3} imgSrc={leaf} des={'잎새'} />
            <LevelItem level={4} imgSrc={twig} des={'가지'} />
            <LevelItem level={5} imgSrc={flower} des={'꽃'} />
            <LevelItem level={6} imgSrc={fruit} des={'열매'} />
            <LevelItem level={7} imgSrc={tree} des={'나무'} />
            <LevelItem level={8} imgSrc={forest} des={'숲'} />
            <LevelItem level={9} imgSrc={mountain} des={'산'} />
            <LevelItem level={10} imgSrc={god} des={'산신령'} />
        </div>
    )
}

type LevelItemProps = {
    level: number
    imgSrc: string
    des: string
}

function LevelItem({ level, imgSrc, des }: LevelItemProps) {
    return (
        <div className={styles.level}>
            <span>Lv.{level}</span>
            <img src={imgSrc} />
            <span>{des}</span>
        </div>
    )
}

export default LevelModal
