package com.yurwar
package common.strategy.optimization.impl.blocking

import common.entity.Relation
import common.entity.RelationProperty.Asymmetry
import common.strategy.optimization.AbstractDominationBlockingOptimizationStrategy

class StrictBlockingOptimizationStrategy extends AbstractDominationBlockingOptimizationStrategy(Set.empty, Set(Asymmetry)) {

  override protected def isElementOptimal(el: Int, rel: Relation): Boolean = {
    rel.getUpperSection(el)
      .forall(sectionEl => el == sectionEl)
  }
}
