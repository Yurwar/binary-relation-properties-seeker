package com.yurwar
package common.strategy.criteria

import common.entity.{Criteria, Relation}

trait RelationBuildingStrategy {
  def buildByCriteria(criteria: Criteria): Relation
}
