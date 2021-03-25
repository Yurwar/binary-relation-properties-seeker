package com.yurwar
package cp5.solver

import common.strategy.multicriteria.{MultiCriteriaRankingStrategy, SimpleTopsisRankingStrategy}
import common.util.TaskFileReader

class VikorRankFindingSolver extends Solver {
  val topsisRankingStrategy: MultiCriteriaRankingStrategy = new SimpleTopsisRankingStrategy
  val taskFileReader: TaskFileReader = new TaskFileReader

  override def solve(): Unit = {

  }
}
