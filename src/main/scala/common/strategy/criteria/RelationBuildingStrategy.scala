package com.yurwar
package common.strategy.criteria

import common.entity.{SimpleCriteria, Relation}

trait RelationBuildingStrategy {
  def buildByCriteria(criteria: SimpleCriteria): Relation
}
