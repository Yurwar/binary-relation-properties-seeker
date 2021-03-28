package com.yurwar
package common.strategy.property.fuzzy.impl.symmetry

import common.entity.RelationProperty.Asymmetry
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy.FuzzyPropertyCheckStrategy

class AsymmetryCheckStrategy extends FuzzyPropertyCheckStrategy {
  override def check(relation: FuzzyRelation): PropertyCheckResult = {
    for (i <- relation.matrix.indices;
         j <- relation.matrix(i).indices) {
      if (math.min(relation.matrix(i)(j), relation.matrix(j)(i)) != 0) {
        return populateCheckResult(Asymmetry, List((i, j), (j, i)))
      }
    }
    populateCheckResult(Asymmetry, List.empty)
  }
}
