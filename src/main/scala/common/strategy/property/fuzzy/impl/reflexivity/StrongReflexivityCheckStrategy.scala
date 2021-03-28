package com.yurwar
package common.strategy.property.fuzzy.impl.reflexivity

import common.entity.RelationProperty.StrongReflexivity
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class StrongReflexivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  val reflexivityCheckStrategy = new ReflexivityCheckStrategy

  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    val reflexivityCheckResult = reflexivityCheckStrategy.check(relation)

    if (!reflexivityCheckResult.present) {
      new PropertyCheckResult(reflexivityCheckResult.present,
        new PropertyViolation(StrongReflexivity, reflexivityCheckResult.propertyViolation.violationPoints))
    } else {
      for (i <- relation.matrix.indices;
           j <- relation.matrix(i).indices if i != j) {
        if (relation.matrix(i)(j) == 1.0) {
          return new PropertyCheckResult(false, new PropertyViolation(StrongReflexivity, List((i, j))))
        }
      }
      new PropertyCheckResult(true, new PropertyViolation(StrongReflexivity, List.empty))
    }
  }
}
