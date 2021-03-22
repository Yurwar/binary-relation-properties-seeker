package com.yurwar
package cp2.solver

import common.entity.Relation
import common.service.RelationService
import common.strategy.optimization.OptimizationStrategy
import common.util.ConsolePrinter

import scala.collection.mutable

abstract class AbstractOptimizationSolver(relationService: RelationService) extends Solver {

  override def solve(): Unit

  protected def optimizeRelations(relations: List[Relation], optStrategies: mutable.LinkedHashMap[String, OptimizationStrategy]): Unit = {
    relations.foreach(rel => {
      ConsolePrinter.printRelationMatrix(rel)
      ConsolePrinter.printRelationProperties(rel)
      ConsolePrinter.printRelationClass(rel)
      optStrategies
        .toList
        .filter(nameToStrategy => nameToStrategy._2.isApplicable(rel))
        .map(nameToStrategy => s"${nameToStrategy._1} = ${nameToStrategy._2.getOptimalElements(rel).map(_ + 1).mkString(", ")}")
        .foreach(println)
      println
    })
  }

  protected def populateRelations(relations: List[Relation]): Unit = {
    relations.foreach(rel => {
      rel.relationProperties.addAll(relationService.findPropertiesForRelation(rel))
      rel.relationClass = relationService.findClassForRelation(rel)
    })
  }
}
