package com.yurwar
package cp5.solver

import common.strategy.multicriteria.vikor.{DefaultVikorRankingStrategy, VikorRankingStrategy}
import common.util.TaskFileReader

class VikorRankFindingSolver extends Solver {
  val vikorRankingStrategy: VikorRankingStrategy = new DefaultVikorRankingStrategy
  val taskFileReader: TaskFileReader = new TaskFileReader

  override def solve(): Unit = {
    val criteria = taskFileReader.parseVikorCriteria("src/main/resources/cp5/criteria_vikor_task2.txt")

    println(s"${vikorRankingStrategy.findRankedAlternatives(criteria).map(el => "x" + (el + 1)).mkString(" > ")}")
  }
}
