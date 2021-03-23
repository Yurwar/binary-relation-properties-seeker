package com.yurwar
package common.strategy.criteria.impl
import common.entity.SimpleCriteria

class ParetoRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override def findSingleRelation(x: Int, y: Int, criteria: SimpleCriteria): Boolean = {
    findSingleDelta(x, y, criteria).forall(sign => sign >= 0)
  }
}
