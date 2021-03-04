package com.yurwar
package strategy.property.impl

import entity.Relation
import strategy.property.PropertyCheckStrategy

class ReflexivityCheckStrategy extends PropertyCheckStrategy{
  override def hasProperty(relation: Relation): Boolean = {
    for (idx <- 0 until relation.size) {
      if (relation.getElement(idx, idx).equals(false)) {
        return false
      }
    }

    true
  }
}
