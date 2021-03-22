package com.yurwar
package common.strategy.criteria.impl

import common.entity.Criteria

class MajorityRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override def findSingleRelation(x: Int, y: Int, criteria: Criteria): Boolean = {
    findSingleDelta(x, y, criteria).sum > 0
  }
}
