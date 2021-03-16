package com.yurwar
package common.strategy.property.impl

import common.entity.RelationProperty.AntiReflexivity
import common.entity.{PropertyCheckResult, PropertyViolation, Relation}
import common.strategy.property.PropertyCheckStrategy

class AntiReflexivityCheckStrategy extends PropertyCheckStrategy {

  override def check(relation: Relation): PropertyCheckResult = {
    for (idx <- 0 until relation.size) {
      if (relation.getElement(idx, idx).equals(true)) {
        return new PropertyCheckResult(false, new PropertyViolation(AntiReflexivity, List((idx, idx))))
      }
    }

    new PropertyCheckResult(true, new PropertyViolation(AntiReflexivity, List.empty))
  }
}
