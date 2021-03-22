package com.yurwar
package cp1.solver

import common.entity.{Relation, RelationClasses}
import common.util.{ConsolePrinter, TaskFileReader, Utils}

class RelationClassSeekSolver extends Solver {
  val taskFileName = "src/main/resources/cp1/binary_relations_task1.txt"

  override def solve(): Unit = {
    val a: TaskFileReader = new TaskFileReader

    val relations = a.parseRelations(taskFileName)

    runPropertyCheckStrategies(relations)

    findClassForRelation(relations)

    relations.foreach(ConsolePrinter.printRelation)
  }

  private def findClassForRelation(relations: List[Relation]): Unit = {
    relations.foreach(rel => {
      RelationClasses.values.toList.sortBy(_.props.size).foreach(rc => {
        if (rc.props.forall(rel.relationProperties.contains)) {
          rel.relationClass = rc
        }
      })
    })
  }

  private def runPropertyCheckStrategies(relations: List[Relation]): Unit = {
    relations.foreach(rel => {
      Utils.PropertyStrategies.foreach(propStrategy => {
        val checkResult = propStrategy._2.check(rel)
        if (checkResult.present) {
          rel.relationProperties.addOne(propStrategy._1)
        } else {
          rel.propertyViolations.addOne(checkResult.propertyViolation)
        }
      })
    })
  }
}
