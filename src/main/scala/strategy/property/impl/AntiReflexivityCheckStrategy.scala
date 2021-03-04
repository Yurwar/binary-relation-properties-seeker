package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class AntiReflexivityCheckStrategy extends PropertyCheckStrategy{

  override def hasProperty(relation: Relation): Boolean = {
    for (idx <- 0 until relation.size) {
      if (relation.getElement(idx, idx).equals(true)) {
        return false
      }
    }

    true
  }
}
