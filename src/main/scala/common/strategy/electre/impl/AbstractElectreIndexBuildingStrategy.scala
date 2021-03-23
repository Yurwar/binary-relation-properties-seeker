package com.yurwar
package common.strategy.electre.impl

import common.entity.ElectreCriteria
import common.strategy.electre.ElectreIndexBuildingStrategy

abstract class AbstractElectreIndexBuildingStrategy extends ElectreIndexBuildingStrategy {

  def calculateIndexForElement(x: Int, y: Int, criteria: ElectreCriteria): Double

  override def buildByCriteria(criteria: ElectreCriteria): IndexMatrix = {
    criteria.alternativesRating.indices
      .map(x => criteria.alternativesRating.indices.map(y => {
        calculateIndexForElement(x, y, criteria)
      }).toList).toList
  }
}
