package com.yurwar
package cp3.solver

import common.entity.Relation
import common.service.RelationService
import common.strategy.optimization.OptimizationStrategy
import common.util.{ConsolePrinter, ResultFileWriter, TaskFileReader, Utils}

import scala.collection.mutable

class RelationByCriteriaSolver(relationService: RelationService) extends Solver {
  private val InputFileName = "src/main/resources/cp3/criteria_task1.txt"
  private val OutputFileName = "src/main/resources/cp3/Var-19-МатьораЮра.txt"

  override def solve(): Unit = {
    val taskFileReader: TaskFileReader = new TaskFileReader
    val resultFileWriter: ResultFileWriter = new ResultFileWriter

    val criteria = taskFileReader.parseCriteria(InputFileName)

    val relations = Utils.RelationBuildingStrategies.map(strategy => {
      (strategy._1, strategy._2.buildByCriteria(criteria))
    }).map(strategyRelation => {
      strategyRelation._2.relationProperties.addAll(relationService.findPropertiesForRelation(strategyRelation._2))
      strategyRelation._2.relationClass = relationService.findClassForRelation(strategyRelation._2)

      println(strategyRelation._1)
      ConsolePrinter.printRelationMatrix(strategyRelation._2)
      ConsolePrinter.printRelationProperties(strategyRelation._2)
      ConsolePrinter.printRelationClass(strategyRelation._2)
      optimizeRelation(strategyRelation._2, Utils.DominationBlockingStrategies)
      optimizeRelation(strategyRelation._2, Utils.NmKOptimizationStrategies)
      println()
      strategyRelation._2
    }).toList

    resultFileWriter.writeRelations(OutputFileName, relations)

  }

  private def optimizeRelation(rel: Relation, optStrategies: mutable.LinkedHashMap[String, OptimizationStrategy]) = {
    optStrategies
      .toList
      .filter(nameToStrategy => nameToStrategy._2.isApplicable(rel))
      .map(nameToStrategy => s"${nameToStrategy._1} = ${nameToStrategy._2.getOptimalElements(rel).map(_ + 1).mkString(", ")}")
      .foreach(println)
  }
}
