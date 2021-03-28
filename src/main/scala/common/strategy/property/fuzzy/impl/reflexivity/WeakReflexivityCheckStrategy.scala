package com.yurwar
package common.strategy.property.fuzzy.impl.reflexivity

import common.entity.RelationProperty.WeakReflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class WeakReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices) {
      if (relation.matrix(i)(j) > relation.matrix(i)(i)) {
        return populateCheckResult(WeakReflexivity, List((i, j)))
      }
    }
    populateCheckResult(WeakReflexivity, List.empty)
  }
}
