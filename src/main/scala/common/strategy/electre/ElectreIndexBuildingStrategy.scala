package com.yurwar
package common.strategy.electre

import common.entity.ElectreCriteria

trait ElectreIndexBuildingStrategy extends ElectreBuildingStrategy {
  def buildByCriteria(criteria: ElectreCriteria): IndexMatrix
}
