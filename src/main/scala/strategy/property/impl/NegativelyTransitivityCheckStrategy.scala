package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class NegativelyTransitivityCheckStrategy extends PropertyCheckStrategy {
  val transitivityCheckStrategy: TransitivityCheckStrategy = new TransitivityCheckStrategy

  override def hasProperty(relation: Relation): Boolean = {
    transitivityCheckStrategy.hasProperty(new Relation(relation.invertedMatrix))
  }
}
