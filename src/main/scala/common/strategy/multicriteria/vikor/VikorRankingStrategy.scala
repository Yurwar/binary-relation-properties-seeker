package com.yurwar
package common.strategy.multicriteria.vikor

import common.entity.VikorCriteria
import common.strategy.multicriteria.MultiCriteriaRankingStrategy

trait VikorRankingStrategy extends MultiCriteriaRankingStrategy {

  def findRankedAlternatives(criteria: VikorCriteria): List[Int]
}
