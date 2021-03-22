package com.yurwar
package common.strategy.criteria.impl

import common.entity.{Criteria, Relation}
import common.strategy.criteria.RelationBuildingStrategy

class PodinovskyRelationBuildingStrategy extends RelationBuildingStrategy {
  val paretoRelationBuildingStrategy = new ParetoRelationBuildingStrategy

  override def buildByCriteria(criteria: Criteria): Relation = {
    new Relation(paretoRelationBuildingStrategy.buildByCriteria(new Criteria(
      criteria.alternativesRating.map(_.sorted).map(_.reverse),
      criteria.strictDescCriterionOrder,
      criteria.quasiAscCriterionGroupOrder
    )).matrix)
  }
}
