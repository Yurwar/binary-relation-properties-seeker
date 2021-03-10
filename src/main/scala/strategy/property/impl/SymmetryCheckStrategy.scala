package com.yurwar
package strategy.property.impl

import entity.RelationProperty.Symmetry
import entity.{PropertyCheckResult, PropertyViolation, Relation}
import strategy.property.PropertyCheckStrategy

class SymmetryCheckStrategy extends PropertyCheckStrategy {
  override def check(relation: Relation): PropertyCheckResult = {
    for (i <- 0 until relation.size;
         j <- i + 1 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (upperRelation != lowerRelation)
        return new PropertyCheckResult(false, new PropertyViolation(Symmetry, List((i, j))))
    }

    new PropertyCheckResult(true, new PropertyViolation(Symmetry, List.empty))
  }
}
