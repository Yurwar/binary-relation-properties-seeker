package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class TransitivityCheckStrategy extends PropertyCheckStrategy {

  override def hasProperty(relation: Relation): Boolean = {
    Range(0, relation.size)
      .map(idx => checkRowTransitivity(idx, relation))
      .forall(_ == true)
  }

  private def checkRowTransitivity(idx: Int, relation: Relation): Boolean = {
    relation.getLowerSection(idx)
      .forall(rowIdx => {
        Range(0, relation.size)
          .filter(columnIdx => relation.getElement(rowIdx, columnIdx))
          .forall(columnIdx => relation.getElement(idx, columnIdx))
      })
  }
}
