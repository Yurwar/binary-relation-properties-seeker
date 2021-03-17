package com.yurwar
package cp2.solver

import common.service.RelationService
import common.util.{RelationReader, Utils}

class DominationAndBlockingSolver(relationService: RelationService) extends AbstractOptimizationSolver(relationService) {
  override def solve(): Unit = {
    val relationReader = new RelationReader

    val relations = relationReader.parseRelations("src/main/resources/cp2/binary_relations_task1.txt")

    populateRelations(relations)

    optimizeRelations(relations, Utils.DominationBlockingStrategies)
  }
}
