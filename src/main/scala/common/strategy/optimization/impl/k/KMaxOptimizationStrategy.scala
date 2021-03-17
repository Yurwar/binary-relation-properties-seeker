package com.yurwar
package common.strategy.optimization.impl.k

class KMaxOptimizationStrategy(k: Int) extends AbstractKOptimizationStrategy(k) {

  override protected def filterCriteriaMatchedElements(sections: List[List[Int]]): List[Int] = {
    sections.indices
      .filter(idx => sections.forall(section => section.toSet.subsetOf(sections(idx).toSet)))
      .toList
  }
}
