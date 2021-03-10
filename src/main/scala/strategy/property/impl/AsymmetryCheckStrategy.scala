package com.yurwar
package strategy.property.impl

import entity.RelationProperty.Asymmetry
import entity.{PropertyCheckResult, PropertyViolation, Relation}
import strategy.property.PropertyCheckStrategy

class AsymmetryCheckStrategy extends PropertyCheckStrategy {
  override def check(relation: Relation): PropertyCheckResult = {
    for (i <- 0 until relation.size;
         j <- 0 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (i == j) {
        if (upperRelation) {
          return new PropertyCheckResult(false, new PropertyViolation(Asymmetry, List((i, j))))
        }
      } else {
        if (upperRelation && lowerRelation) {
          return new PropertyCheckResult(false, new PropertyViolation(Asymmetry, List((i, j))))
        }
      }
    }

    new PropertyCheckResult(true, new PropertyViolation(Asymmetry, List.empty))
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