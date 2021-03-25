package com.yurwar
package common.strategy.multicriteria

import common.entity.{BenefitCriterion, CostCriterion, CriterionType}

class CriterionTypedTopsisRankingStrategy extends TopsisRankingStrategy {
  protected def normalizeRating(alternativesRating: List[List[Double]], criterionTypes: List[CriterionType]): List[List[Double]] = {
    val desiredValues = findDesiredValues(alternativesRating, criterionTypes)
    val worstValues = findWorstValues(alternativesRating, criterionTypes)

    alternativesRating.indices.map(row => {
      alternativesRating.head.indices.map(col => {
        criterionTypes(col) match {
          case BenefitCriterion() =>
            (alternativesRating(row)(col) - worstValues(col)) / (desiredValues(col) - worstValues(col))
          case CostCriterion() =>
            (desiredValues(col) - alternativesRating(row)(col)) / (desiredValues(col) - worstValues(col))
        }
      }).toList
    }).toList
  }
}
