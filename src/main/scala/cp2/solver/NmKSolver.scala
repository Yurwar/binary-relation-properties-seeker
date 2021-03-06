package com.yurwar
package cp2.solver

import common.service.RelationService
import common.util.{TaskFileReader, Utils}

class NmKSolver(relationService: RelationService) extends AbstractOptimizationSolver(relationService) {
  val TaskFileName = "src/main/resources/cp2/binary_relations_task2.txt"

  override def solve(): Unit = {
    val relationReader = new TaskFileReader
    val relations = relationReader.parseRelations(TaskFileName)

    populateRelations(relations)

    optimizeRelations(relations, Utils.NmKOptimizationStrategies)
  }
}
