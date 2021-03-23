package com.yurwar
package common.strategy.electre.impl

import common.entity.ElectreCriteria

class ElectreDiscordanceIndexBuildingStrategy extends AbstractElectreIndexBuildingStrategy {

  override def calculateIndexForElement(x: Int, y: Int, criteria: ElectreCriteria): Double = {
    if (x == y) {
      1
    } else {
      val xRatings = criteria.alternativesRating(x)
      val yRatings = criteria.alternativesRating(y)

      val suitableIndexes = findSuitableCriteriaIndexes(xRatings, yRatings)
      if (suitableIndexes.nonEmpty) {
        val localMaxDiff = findMaxWeightedDiff(xRatings, yRatings, criteria.weights)

        val globalMaxDiff = findSuitableCriteriaIndexes(xRatings, yRatings)
          .map(_._2)
          .map(idx => (findCriterionMaxDiff(idx, criteria), idx))
          .map(tup => tup._1 * criteria.weights(tup._2))
          .maxOption
          .getOrElse(0.0)

        localMaxDiff.toDouble / globalMaxDiff.toDouble
      } else {
        0
      }
    }
  }

  def findCriterionMaxDiff(idx: Int, criteria: ElectreCriteria): Double = {
    val singleCriterionValues = criteria.alternativesRating.map(row => row(idx))

    singleCriterionValues.max - singleCriterionValues.min
  }

  private def findMaxWeightedDiff(xRatings: List[Int], yRatings: List[Int], weights: List[Int]): Int = {
    findSuitableCriteriaIndexes(xRatings, yRatings)
      .map(tup => {
        val xyTup = tup._1
        (xyTup._2 - xyTup._1, tup._2)
      })
      .map(tup => {
        tup._1 * weights(tup._2)
      })
      .maxOption
      .getOrElse(0)
  }

  private def findSuitableCriteriaIndexes(xRatings: List[Int], yRatings: List[Int]) = {
    xRatings
      .zip(yRatings)
      .zipWithIndex
      .filter(tup => {
        val xyTup = tup._1
        xyTup._1 < xyTup._2
      })
  }
}
