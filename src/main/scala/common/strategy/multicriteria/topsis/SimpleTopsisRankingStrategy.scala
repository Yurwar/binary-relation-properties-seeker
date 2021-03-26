package com.yurwar
package common.strategy.multicriteria.topsis

import common.entity.CriterionType

class SimpleTopsisRankingStrategy extends AbstractTopsisRankingStrategy {
  protected def normalizeRating(alternativesRating: List[List[Double]], criterionType: List[CriterionType]): List[List[Double]] = {
    val normalizers = calculateNormalizers(alternativesRating)

    alternativesRating.indices.map(row => {
      alternativesRating.head.indices.map(col => {
        alternativesRating(row)(col) / normalizers(col)
      }).toList
    }).toList
  }

  private def calculateNormalizers(alternativesRating: List[List[Double]]): List[Double] = {
    val normalizers = alternativesRating.head.indices.map(col => {
      val column = alternativesRating.indices.map(row => {
        alternativesRating(row)(col)
      }).toList

      calculateColumnNormalizer(column)
    }).toList
    normalizers
  }

  private def calculateColumnNormalizer(column: List[Double]): Double = {
    math.sqrt(column
      .map(math.pow(_, 2)).sum)
  }
}
