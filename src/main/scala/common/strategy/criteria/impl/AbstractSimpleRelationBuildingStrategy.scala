package com.yurwar
package common.strategy.criteria.impl

import common.entity.{Criteria, Relation}
import common.strategy.criteria.RelationBuildingStrategy


abstract class AbstractSimpleRelationBuildingStrategy extends RelationBuildingStrategy {

  protected def findSingleRelation(x: Int, y: Int, criteria: Criteria): Boolean

  override def buildByCriteria(criteria: Criteria): Relation = {
    val relationMatrix = criteria.alternativesRating.indices
      .map(row => criteria.alternativesRating.indices
        .map(col => findSingleRelation(row, col, criteria)).toList).toList

    new Relation(relationMatrix)
  }

  protected def findSingleDelta(x: Int, y: Int, criteria: Criteria) = {
    criteria.alternativesRating.head.indices
      .map(criterionIdx => (criteria.alternativesRating(x)(criterionIdx) - criteria.alternativesRating(y)(criterionIdx)).compare(0)).toList
  }
}
