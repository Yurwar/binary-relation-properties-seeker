package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class AsymmetryCheckStrategy extends PropertyCheckStrategy {
  override def hasProperty(relation: Relation): Boolean = {
    for (i <- 0 until relation.size;
         j <- 0 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (i == j) {
        if (upperRelation) {
          return false
        }
      } else {
        if (upperRelation && lowerRelation) {
          return false
        }
      }
    }

    true
  }
}

//class AsymmetryCheckStrategy extends PropertyCheckStrategy {
//  private val antiReflexivityCheckStrategy: AntiReflexivityCheckStrategy = new AntiReflexivityCheckStrategy
//  private val transitivityCheckStrategy: TransitivityCheckStrategy = new TransitivityCheckStrategy
//
//  override def hasProperty(relation: Relation): Boolean = {
//    antiReflexivityCheckStrategy.hasProperty(relation) &&
//      transitivityCheckStrategy.hasProperty(relation)
//  }
//}