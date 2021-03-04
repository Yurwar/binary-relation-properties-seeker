package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class AntiSymmetryCheckStrategy extends PropertyCheckStrategy {
  override def hasProperty(relation: Relation): Boolean = {
    Range(0, relation.size - 1)
      .forall(rowIdx =>
        Range(rowIdx + 1, relation.size)
          .forall(columnIdx => {
            !(relation.getElement(rowIdx, columnIdx) && relation.getElement(columnIdx, rowIdx))
          }))
  }
}
