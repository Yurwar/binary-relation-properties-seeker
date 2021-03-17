package com.yurwar
package common.strategy.optimization.impl.blocking

import common.entity.Relation
import common.entity.RelationProperty.Asymmetry
import common.strategy.optimization.AbstractDominationBlockingOptimizationStrategy

class AsymmetryBlockingOptimizationStrategy extends AbstractDominationBlockingOptimizationStrategy(Set(Asymmetry), Set.empty) {

  override protected def isElementOptimal(el: Int, rel: Relation): Boolean = {
    rel.getUpperSection(el).isEmpty
  }
}
