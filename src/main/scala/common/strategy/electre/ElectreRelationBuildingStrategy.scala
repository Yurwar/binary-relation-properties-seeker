package com.yurwar
package common.strategy.electre

import common.entity.{ElectreCriteria, Relation}

trait ElectreRelationBuildingStrategy extends ElectreBuildingStrategy {
  def buildByCriteria(criteria: ElectreCriteria): Relation
}
