package com.yurwar
package common.strategy.property.fuzzy.impl.antireflexivity

import common.entity.RelationProperty.WeakAntiReflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class WeakAntiReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices if i != j) {
      if (relation.matrix(i)(j) < relation.matrix(i)(i)) {
        return populateCheckResult(WeakAntiReflexivity, List((i, j)))
      }
    }
    populateCheckResult(WeakAntiReflexivity, List.empty)
  }
}
