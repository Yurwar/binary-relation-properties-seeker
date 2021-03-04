package com.yurwar
package strategy.property

import entity.Relation

trait PropertyCheckStrategy {
  def hasProperty(relation: Relation): Boolean
}
