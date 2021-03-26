package com.yurwar
package common.strategy.multicriteria.topsis

import common.entity.TopsisCriteria
import common.strategy.multicriteria.MultiCriteriaRankingStrategy

trait TopsisRankingStrategy extends MultiCriteriaRankingStrategy {
  def findRankedAlternatives(criteria: TopsisCriteria): List[Int]

}
