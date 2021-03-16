package com.yurwar
package common.strategy.property.impl

import common.entity.RelationProperty.Transitivity
import common.entity.{PropertyCheckResult, PropertyViolation, Relation}
import common.strategy.property.PropertyCheckStrategy

class TransitivityCheckStrategy extends PropertyCheckStrategy {

  override def check(relation: Relation): PropertyCheckResult = {
    val transitivityViolations = Range(0, relation.size)
      .flatMap(idx => checkRowTransitivityViolations(idx, relation))
      .toList

    if (transitivityViolations.isEmpty) {
      new PropertyCheckResult(true, new PropertyViolation(Transitivity, List.empty))
    } else {
      new PropertyCheckResult(false, new PropertyViolation(Transitivity, transitivityViolations))
    }
  }

  private def checkRowTransitivityViolations(idx: Int, relation: Relation): List[(Int, Int)] = {
    relation.getLowerSection(idx)
      .flatMap(rowIdx => {
        Range(0, relation.size)
          .filter(columnIdx => relation.getElement(rowIdx, columnIdx))
          .filter(columnIdx => !relation.getElement(idx, columnIdx))
          .map(columnIdx => (idx, columnIdx))
      })
  }
}
