package com.yurwar
package common.strategy.property.fuzzy.impl.antireflexivity

import common.entity.RelationProperty.StrongAntiReflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class StrongAntiReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  val antiReflexivityCheckStrategy = new AntiReflexivityCheckStrategy

  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    val antiReflexivityCheckResult = antiReflexivityCheckStrategy.check(relation)

    if (!antiReflexivityCheckResult.present) {
      new PropertyCheckResult(antiReflexivityCheckResult.present,
        new PropertyViolation(StrongAntiReflexivity, antiReflexivityCheckResult.propertyViolation.violationPoints))
    } else {
      for (i <- relation.matrix.indices;
           j <- relation.matrix(i).indices if i != j) {
        if (relation.matrix(i)(j) == 0.0) {
          return new PropertyCheckResult(false, new PropertyViolation(StrongAntiReflexivity, List((i, j))))
        }
      }
      new PropertyCheckResult(true, new PropertyViolation(StrongAntiReflexivity, List.empty))
    }
  }
}
