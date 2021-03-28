package com.yurwar
package common.strategy.property.fuzzy.impl.connectivity

import common.entity.RelationProperty.StrongConnectivity
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class StrongConnectivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices) {
      if (math.max(relation.matrix(i)(j), relation.matrix(j)(i)) != 1) {
        return populateCheckResult(StrongConnectivity, List((i, j), (j, i)))
      }
    }
    populateCheckResult(StrongConnectivity, List.empty)
  }
}
