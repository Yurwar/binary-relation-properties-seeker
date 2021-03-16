package com.yurwar
package cp1.solver

import common.entity.RelationClasses
import common.util.{RelationPrinter, RelationReader}

class PreferenceRestoreSolver extends AbstractRestoreSolver {
  val taskFileName = "src/main/resources/cp1/binary_relations_task2.2.txt"

  override def solve(): Unit = {
    val relationReader = new RelationReader

    val relations = relationReader.parseRelations(taskFileName)

    val relationForQuasiOrder = relations.head
    val relationForWeakOrdering = relations.tail.head

    println(s"Start restoring ${RelationClasses.QuasiOrder} for relation")
    RelationPrinter.printRelationMatrix(relationForQuasiOrder)
    restoreClassForRelation(relationForQuasiOrder, RelationClasses.QuasiOrder)

    println(s"Start restoring ${RelationClasses.WeakOrdering} for relation")
    RelationPrinter.printRelationMatrix(relationForWeakOrdering)
    restoreClassForRelation(relationForWeakOrdering, RelationClasses.WeakOrdering)
  }
}
