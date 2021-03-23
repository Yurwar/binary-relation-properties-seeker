package com.yurwar
package common.strategy.electre.impl

import common.entity.ElectreCriteria

class ElectreConcordanceIndexBuildingStrategy extends AbstractElectreIndexBuildingStrategy {
  override def calculateIndexForElement(x: Int, y: Int, criteria: ElectreCriteria): Double = {
    if (x == y) {
      0
    } else {
      val concordanceWeightsSum = criteria.alternativesRating(x)
        .zip(criteria.alternativesRating(y))
        .zipWithIndex
        .filter(tup => {
          val xyTup = tup._1
          xyTup._1 >= xyTup._2
        })
        .map(_._2)
        .map(criteria.weights(_))
        .sum

      concordanceWeightsSum.toDouble / criteria.weights.sum.toDouble
    }
  }
}
