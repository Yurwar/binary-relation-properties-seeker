package com.yurwar
package common.strategy.criteria.impl

import common.entity.Criteria

class LexicographyRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override protected def findSingleRelation(x: Int, y: Int, criteria: Criteria): Boolean = {
    criteria.strictDescCriterionOrder
      .map(findSingleDelta(x, y, criteria))
      .find(_ != 0)
      .getOrElse(-1) == 1
  }
}
