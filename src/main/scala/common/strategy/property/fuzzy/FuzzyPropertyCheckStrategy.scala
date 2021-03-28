package com.yurwar
package common.strategy.property.fuzzy

import common.entity.RelationProperty.RelationProperty
import common.entity.{FuzzyRelation, PropertyCheckResult, PropertyViolation}

trait FuzzyPropertyCheckStrategy {
  def check(relation: FuzzyRelation): PropertyCheckResult

  protected def populateCheckResult(relationProperty: RelationProperty,
                                    violationPoints: List[(Int, Int)]): PropertyCheckResult = {
    new PropertyCheckResult(violationPoints.isEmpty, new PropertyViolation(relationProperty, violationPoints))
  }
}
