package com.yurwar
package common.strategy.criteria.impl
import common.entity.Criteria

class ParetoRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override def findSingleRelation(x: Int, y: Int, criteria: Criteria): Boolean = {
    findSingleDelta(x, y, criteria).forall(sign => sign >= 0)
  }
}
