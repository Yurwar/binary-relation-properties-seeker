package com.yurwar
package cp5.solver

import common.strategy.multicriteria.{CriterionTypedTopsisRankingStrategy, SimpleTopsisRankingStrategy, TopsisRankingStrategy}
import common.util.{ConsolePrinter, TaskFileReader}

class TopsisRankFindingSolver extends Solver {
  val simpleTopsisRankingStrategy: TopsisRankingStrategy = new SimpleTopsisRankingStrategy
  val criterionTypedTopsisRankingStrategy: TopsisRankingStrategy = new CriterionTypedTopsisRankingStrategy
  val taskFileReader: TaskFileReader = new TaskFileReader

  override def solve(): Unit = {
    val maximizedCriteria = taskFileReader.parseTopsisCriteria("src/main/resources/cp5/criteria_topsis_task1.1.txt")
    val mixedCriteria = taskFileReader.parseTopsisCriteria("src/main/resources/cp5/criteria_topsis_task1.2.txt")


    ConsolePrinter.printCriteriaRating(maximizedCriteria)
    ConsolePrinter.printCriteriaWeights(maximizedCriteria)
    ConsolePrinter.printCriteriaTypes(maximizedCriteria)
    println(simpleTopsisRankingStrategy.findRankedAlternatives(maximizedCriteria).map(el => "x" + (el + 1)).mkString(" > "))

    println()
    println()
    println()

    ConsolePrinter.printCriteriaRating(mixedCriteria)
    ConsolePrinter.printCriteriaWeights(mixedCriteria)
    ConsolePrinter.printCriteriaTypes(mixedCriteria)
    println(criterionTypedTopsisRankingStrategy.findRankedAlternatives(mixedCriteria).map(el => "x" + (el + 1)).mkString(" > "))
  }
}
