package com.yurwar
package strategy.property.impl

import entity.RelationProperty.AntiSymmetry
import entity.{PropertyCheckResult, PropertyViolation, Relation}
import strategy.property.PropertyCheckStrategy

class AntiSymmetryCheckStrategy extends PropertyCheckStrategy {
  override def check(relation: Relation): PropertyCheckResult = {
    for (rowIdx <- 0 until relation.size - 1) {
      for (colIdx <- rowIdx + 1 until relation.size) {
        val res = relation.getElement(rowIdx, colIdx) && relation.getElement(colIdx, rowIdx)
        if (res) {
          return new PropertyCheckResult(false, new PropertyViolation(AntiSymmetry, List((rowIdx, colIdx))))
        }
      }
    }

    new PropertyCheckResult(true, new PropertyViolation(AntiSymmetry, List.empty))
    //    Range(0, relation.size - 1)
    //      .forall(rowIdx =>
    //        Range(rowIdx + 1, relation.size)
    //          .forall(columnIdx => {
    //            !(relation.getElement(rowIdx, columnIdx) && relation.getElement(columnIdx, rowIdx))
    //          }))
  }
}
