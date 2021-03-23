package com.yurwar
package common.strategy.criteria.impl

import common.entity.SimpleCriteria

class MajorityRelationBuildingStrategy extends AbstractSimpleRelationBuildingStrategy {

  override def findSingleRelation(x: Int, y: Int, criteria: SimpleCriteria): Boolean = {
    findSingleDelta(x, y, criteria).sum > 0
  }
}
