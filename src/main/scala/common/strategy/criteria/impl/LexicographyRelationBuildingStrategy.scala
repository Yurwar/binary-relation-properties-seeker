package com.yurwar
package common.strategy.criteria.impl

import common.entity.SimpleCriteria

class LexicographyRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override protected def findSingleRelation(x: Int, y: Int, criteria: SimpleCriteria): Boolean = {
    criteria.strictDescCriterionOrder
      .map(findSingleDelta(x, y, criteria))
      .find(_ != 0)
      .getOrElse(-1) == 1
  }
}
