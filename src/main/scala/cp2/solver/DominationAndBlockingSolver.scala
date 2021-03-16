package com.yurwar
package cp2.solver

import common.service.{DefaultRelationService, RelationService}
import common.util.{RelationPrinter, RelationReader, Utils}

class DominationAndBlockingSolver extends Solver {
  val relationService: RelationService = new DefaultRelationService

  override def solve(): Unit = {
    val relationReader = new RelationReader

    val relations = relationReader.parseRelations("src/main/resources/cp2/binary_relations_task1.txt")

    relations.foreach(rel => rel.relationProperties.addAll(relationService.findPropertiesForRelation(rel)))

    relations.foreach(rel => {
      RelationPrinter.printRelationMatrix(rel)
      RelationPrinter.printRelationProperties(rel)
      Utils.DominationBlockingStrategies
        .toList
        .filter(nameToStrategy => nameToStrategy._2.isApplicable(rel))
        .map(nameToStrategy => s"${nameToStrategy._1} = ${nameToStrategy._2.getOptimalElements(rel).map(_ + 1).mkString(", ")}")
        .foreach(println)
      println
    })
  }
}
