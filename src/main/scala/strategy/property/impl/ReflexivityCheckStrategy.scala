package com.yurwar
package strategy.property.impl

import entity.RelationProperty._
import entity.{PropertyCheckResult, PropertyViolation, Relation}
import strategy.property.PropertyCheckStrategy

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
