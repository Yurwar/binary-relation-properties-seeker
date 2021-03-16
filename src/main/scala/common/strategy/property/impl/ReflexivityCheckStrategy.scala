package com.yurwar
package common.strategy.property.impl

import common.entity.RelationProperty._
import common.entity.{PropertyCheckResult, PropertyViolation, Relation}
import common.strategy.property.PropertyCheckStrategy

class ReflexivityCheckStrategy extends PropertyCheckStrategy {
  override def check(relation: Relation): PropertyCheckResult = {
    for (idx <- 0 until relation.size) {
      if (!relation.getElement(idx, idx)) {
        return new PropertyCheckResult(false, new PropertyViolation(Reflexivity, List((idx, idx))))
      }
    }
    new PropertyCheckResult(true, new PropertyViolation(Reflexivity, List.empty))
  }
}
