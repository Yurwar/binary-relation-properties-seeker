package com.yurwar
package common.strategy.property.fuzzy.impl.reflexivity

import common.entity.RelationProperty.Reflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class ReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (idx <- relation.matrix.indices) {
      if (relation.matrix(idx)(idx) != 1) {
        return new PropertyCheckResult(false, new PropertyViolation(Reflexivity, List((idx, idx))))
      }
    }
    new PropertyCheckResult(true, new PropertyViolation(Reflexivity, List.empty))
  }
}
