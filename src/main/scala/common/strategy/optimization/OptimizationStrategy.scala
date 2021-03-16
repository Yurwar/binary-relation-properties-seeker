package com.yurwar
package common.strategy.optimization

import common.entity.Relation

trait OptimizationStrategy {
  def isApplicable(rel: Relation): Boolean

  def getOptimalElements(rel: Relation): List[Int]
}
