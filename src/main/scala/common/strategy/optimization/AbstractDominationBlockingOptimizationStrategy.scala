package com.yurwar
package common.strategy.optimization

import common.entity.Relation
import common.entity.RelationProperty.RelationProperty

abstract class AbstractDominationBlockingOptimizationStrategy(override val requiredProperties: Set[RelationProperty],
                                                              override val forbiddenProperties: Set[RelationProperty]) extends AbstractOptimizationStrategy(requiredProperties, forbiddenProperties) {

  def getOptimalElements(rel: Relation): List[Int] = {
    rel.matrix.indices
      .filter(isElementOptimal(_, rel))
      .toList
  }

  protected def isElementOptimal(el: Int, rel: Relation): Boolean
}
