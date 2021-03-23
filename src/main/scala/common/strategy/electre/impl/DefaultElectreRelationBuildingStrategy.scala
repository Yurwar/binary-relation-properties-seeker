package com.yurwar
package common.strategy.electre.impl

import common.entity.{ElectreCriteria, Relation}
import common.strategy.electre.ElectreRelationBuildingStrategy
import common.util.Utils

class DefaultElectreRelationBuildingStrategy extends ElectreRelationBuildingStrategy {
  override def buildByCriteria(criteria: ElectreCriteria): Relation = {
    val cIdx = buildIndexByCriteria("C", criteria)
    val dIdx = buildIndexByCriteria("D", criteria)

    val comparedCIdx = compareIndex(cIdx, criteria.concordanceThreshold)((a: Double, b: Double) => a >= b)
    val comparedDIdx = compareIndex(dIdx, criteria.discordanceThreshold)((a: Double, b: Double) => a <= b)

    val relationMatrix = criteria.alternativesRating.indices.map(row => {
      criteria.alternativesRating.indices.map(col => {
        if (comparedCIdx(row)(col) == 1 && comparedDIdx(row)(col) == 1) true else false
      }).toList
    }).toList

    new Relation(relationMatrix)
  }

  private def buildIndexByCriteria(idxType: String, criteria: ElectreCriteria) = {
    Utils.IndexBuildingStrategies(idxType).buildByCriteria(criteria)
  }

  private def compareIndex(indexMatrix: IndexMatrix, threshold: Double)
                          (comparator: (Double, Double) => Boolean): List[List[Int]] = {
    indexMatrix.map(row => row.map(el => {
      if (comparator(el, threshold)) 1 else 0
    }))
  }
}
