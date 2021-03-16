package com.yurwar
package common.strategy.optimization.impl.domination

import common.entity.Relation
import common.entity.RelationProperty.Asymmetry
import common.strategy.optimization.AbstractOptimizationStrategy

class StrictDominationOptimizationStrategy extends AbstractOptimizationStrategy(Set.empty, Set(Asymmetry)) {

  override protected def isElementOptimal(el: Int, rel: Relation): Boolean = {
    rel.getLowerSection(el).toSet
      .equals(rel.matrix.indices.toSet) && rel.getUpperSection(el).toSet.equals(Set(el))
  }
}
