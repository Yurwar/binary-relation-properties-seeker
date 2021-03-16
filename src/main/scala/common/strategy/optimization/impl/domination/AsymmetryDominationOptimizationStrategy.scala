package com.yurwar
package common.strategy.optimization.impl.domination

import common.entity.Relation
import common.entity.RelationProperty.Asymmetry
import common.strategy.optimization.AbstractOptimizationStrategy

class AsymmetryDominationOptimizationStrategy extends AbstractOptimizationStrategy(Set(Asymmetry), Set.empty) {

  override protected def isElementOptimal(el: Int, rel: Relation): Boolean = {
    rel.getLowerSection(el)
      .toSet
      .equals(rel.matrix.indices
        .filter(idx => idx != el)
        .toSet)
  }
}