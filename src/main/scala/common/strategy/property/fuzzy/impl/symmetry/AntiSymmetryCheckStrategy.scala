package com.yurwar
package common.strategy.property.fuzzy.impl.symmetry

import common.entity.RelationProperty.AntiSymmetry
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class AntiSymmetryCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices if i != j) {
      if (math.min(relation.matrix(i)(j), relation.matrix(j)(i)) != 0) {
        return populateCheckResult(AntiSymmetry, List((i, j), (j, i)))
      }
    }
    populateCheckResult(AntiSymmetry, List.empty)
  }
}
