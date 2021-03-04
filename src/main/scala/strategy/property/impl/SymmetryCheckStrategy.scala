package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class SymmetryCheckStrategy extends PropertyCheckStrategy {
  override def hasProperty(relation: Relation): Boolean = {
    for (i <- 0 until relation.size;
         j <- i + 1 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (upperRelation != lowerRelation)
        return false
    }

    true
  }
}
