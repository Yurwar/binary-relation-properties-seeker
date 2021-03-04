package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class ConnectivityCheckStrategy extends PropertyCheckStrategy {
  val reflexivityCheckStrategy: ReflexivityCheckStrategy = new ReflexivityCheckStrategy

  override def hasProperty(relation: Relation): Boolean = {
    reflexivityCheckStrategy.hasProperty(relation) && haveNoSymmetricZero(relation)
  }

  private def haveNoSymmetricZero(relation: Relation): Boolean = {
    for (i <- 0 until relation.size;
         j <- i + 1 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (!upperRelation && !lowerRelation)
        return false
    }

    true
  }

}
