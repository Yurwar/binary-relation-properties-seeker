package com.yurwar
package cp4.solver

import common.entity.{ElectreCriteria, Relation}
import common.service.{DefaultRelationService, RelationService}
import common.strategy.electre.ElectreRelationBuildingStrategy
import common.strategy.electre.impl.DefaultElectreRelationBuildingStrategy
import common.strategy.optimization.OptimizationStrategy
import common.strategy.optimization.impl.nm.NmOptimizationStrategy
import common.util.{ResultFileWriter, TaskFileReader, Utils}

class ElectreMethodSolver extends Solver {
  val electreRelationBuildingStrategy: ElectreRelationBuildingStrategy = new DefaultElectreRelationBuildingStrategy
  val nmOptimizationStrategy: OptimizationStrategy = new NmOptimizationStrategy
  val relationService: RelationService = new DefaultRelationService
  val taskFileReader = new TaskFileReader
  val resultFileWriter = new ResultFileWriter("src/main/resources/cp4/Var-19-МатьораЮрій.txt")

  override def solve(): Unit = {
    val criteria = taskFileReader.parseElectreCriteria("src/main/resources/cp4/criteria_electre_task1.txt")

    val resStr = solveFullTaskInternal(criteria)
    println(resStr)
    resultFileWriter.writeText(resStr)
  }

  def solveFullTaskInternal(criteria: ElectreCriteria): String = {
    var relation = electreRelationBuildingStrategy.buildByCriteria(criteria)
    relation.relationProperties.addAll(relationService.findPropertiesForRelation(relation))

    relation = new Relation(relation.matrix, relationService.findClassForRelation(relation), relation.relationProperties)

    buildResultString(criteria, relation)
  }

  private def buildResultString(criteria: ElectreCriteria, relation: Relation): String = {
    val stringBuilder = new StringBuilder

    Utils.IndexBuildingStrategies.foreach(strategy => {
      stringBuilder addAll s"${strategy._1} index matrix\n"
      stringBuilder addAll strategy._2.buildByCriteria(criteria).map(line => s"${line.map("%.3f".format(_)).mkString(" ")}").mkString("\n")
      stringBuilder addAll "\n"
    })

    stringBuilder addAll "Concordance and discordance thresholds\n"
    stringBuilder addAll s"${criteria.concordanceThreshold} ${criteria.discordanceThreshold}"

    stringBuilder addAll "\nRelation for concordance and discordance values\n"
    stringBuilder addAll relation.matrix
      .map(line => s"${
        line.map {
          case true => "1"
          case false => "0"
        }.mkString(" ")
      }").mkString("\n")

    stringBuilder addAll "\nThe core of the binary relation\n"
    if (nmOptimizationStrategy.isApplicable(relation)) {
      stringBuilder addAll s"${
        nmOptimizationStrategy.getOptimalElements(relation)
          .map(_ + 1)
          .sorted
          .mkString(" ")
      }\n"
    } else {
      stringBuilder addAll "∅"
    }

    stringBuilder.result()
  }
}
