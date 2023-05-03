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

function getLevelImg(level: number): string | undefined {
  switch (level) {
    case 1:
      return seed
    case 2:
      return sprout
    case 3:
      return leaf
    case 4:
      return twig
    case 5:
      return flower
    case 6:
      return fruit
    case 7:
      return tree
    case 8:
      return forest
    case 9:
      return mountain
    case 10:
      return god
  }
}

export default getLevelImg
