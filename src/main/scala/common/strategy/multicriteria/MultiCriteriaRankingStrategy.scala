package com.yurwar
package common.strategy.multicriteria

import common.entity.{BenefitCriterion, CostCriterion, CriterionType}

trait MultiCriteriaRankingStrategy {

  protected def findDesiredValues(alternativesRating: List[List[Double]], criterionTypes: List[CriterionType]): List[Double] = {
    alternativesRating.head.indices.map(col => {
      val column = alternativesRating.indices.map(row => {
        alternativesRating(row)(col)
      }).toList

      criterionTypes(col) match {
        case BenefitCriterion() => column.max
        case CostCriterion() => column.min
      }
    }).toList
  }

  protected def findWorstValues(alternativesRating: List[List[Double]], criterionTypes: List[CriterionType]): List[Double] = {
    alternativesRating.head.indices.map(col => {
      val column = alternativesRating.indices.map(row => {
        alternativesRating(row)(col)
      }).toList

      criterionTypes(col) match {
        case BenefitCriterion() => column.min
        case CostCriterion() => column.max
      }
    }).toList
  }
}
