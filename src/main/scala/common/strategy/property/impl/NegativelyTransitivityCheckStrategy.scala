package com.yurwar
package common.strategy.property.impl

import common.entity.{PropertyCheckResult, PropertyViolation, Relation, RelationProperty}
import common.strategy.property.PropertyCheckStrategy

class NegativelyTransitivityCheckStrategy extends PropertyCheckStrategy {
  val transitivityCheckStrategy: TransitivityCheckStrategy = new TransitivityCheckStrategy

  override def check(relation: Relation): PropertyCheckResult = {
    val transitivityRes = transitivityCheckStrategy.check(new Relation(relation.invertedMatrix))
    new PropertyCheckResult(transitivityRes.present, new PropertyViolation(RelationProperty.NegativeTransitivity, transitivityRes.propertyViolation.violationPoints))
  }
}
