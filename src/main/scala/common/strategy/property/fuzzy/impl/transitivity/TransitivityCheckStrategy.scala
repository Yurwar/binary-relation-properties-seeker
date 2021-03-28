package com.yurwar
package common.strategy.property.fuzzy.impl.transitivity

import common.entity.RelationProperty._
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class TransitivityCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    new PropertyCheckResult(relation.compose(relation).subsetOf(relation),
      new PropertyViolation(Transitivity, List.empty))
  }
}
