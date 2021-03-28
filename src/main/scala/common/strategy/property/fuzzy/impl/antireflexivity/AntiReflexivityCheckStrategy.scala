package com.yurwar
package common.strategy.property.fuzzy.impl.antireflexivity

import common.entity.RelationProperty.AntiReflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class AntiReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (idx <- relation.matrix.indices) {
      if (relation.matrix(idx)(idx) != 0) {
        return new PropertyCheckResult(false, new PropertyViolation(AntiReflexivity, List((idx, idx))))
      }
    }
    new PropertyCheckResult(true, new PropertyViolation(AntiReflexivity, List.empty))
  }
}
