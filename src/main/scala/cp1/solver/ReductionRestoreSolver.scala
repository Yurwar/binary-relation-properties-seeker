package com.yurwar
package cp1.solver

import common.entity.{Relation, RelationClasses}
import common.util.{ConsolePrinter, TaskFileReader}

class ReductionRestoreSolver extends AbstractRestoreSolver {
  val taskFileName = "src/main/resources/cp1/binary_relations_task2.1.txt"

  override def solve(): Unit = {
    val a: TaskFileReader = new TaskFileReader

    val relations = a.parseRelations(taskFileName)

    val relationForStrictOrder = relations.head
    val relationForNotStrictOrder = new Relation(relationForStrictOrder.matrix)


    println(s"Start restoring ${RelationClasses.StrictOrder} for relation")
    ConsolePrinter.printRelationMatrix(relationForStrictOrder)
    restoreClassForRelation(relationForStrictOrder, RelationClasses.StrictOrder)

    println(s"Start restoring ${RelationClasses.NotStrictOrder} for relation")
    ConsolePrinter.printRelationMatrix(relationForNotStrictOrder)
    restoreClassForRelation(relationForNotStrictOrder, RelationClasses.NotStrictOrder)
  }
}
