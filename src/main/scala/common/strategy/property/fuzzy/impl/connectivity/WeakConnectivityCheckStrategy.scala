package com.yurwar
package common.strategy.property.fuzzy.impl.connectivity

import common.entity.RelationProperty.WeakConnectivity
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class WeakConnectivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices) {
      if (math.max(relation.matrix(i)(j), relation.matrix(j)(i)) == 0.0) {
        return populateCheckResult(WeakConnectivity, List((i, j), (j, i)))
      }
    }
    populateCheckResult(WeakConnectivity, List.empty)
  }
}
