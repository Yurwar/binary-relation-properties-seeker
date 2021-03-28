package com.yurwar
package common.strategy.property.fuzzy.impl.symmetry

import common.entity.RelationProperty.Symmetry
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class SymmetryCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices) {
      if (relation.matrix(i)(j) != relation.matrix(j)(i)) {
        return populateCheckResult(Symmetry, List((i, j), (j, i)))
      }
    }
    populateCheckResult(Symmetry, List.empty)
  }
}
