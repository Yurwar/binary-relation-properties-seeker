package com.yurwar
package common.strategy.multicriteria

import common.entity.TopsisCriteria

trait MultiCriteriaRankingStrategy {
  def findRankedAlternatives(criteria: TopsisCriteria): List[Int]
}
