package com.yurwar
package common.strategy.optimization

import common.entity.Relation
import common.entity.RelationProperty.RelationProperty

abstract class AbstractOptimizationStrategy(val requiredProperties: Set[RelationProperty],
                                            val forbiddenProperties: Set[RelationProperty]) extends OptimizationStrategy {

  def isApplicable(rel: Relation): Boolean = {
    requiredProperties.subsetOf(rel.relationProperties.toSet) &&
      rel.relationProperties.forall(prop => !forbiddenProperties.contains(prop))
  }
}
