package com.yurwar
package cp6.solver

import common.service.DefaultFuzzyRelationService
import common.util.{ConsolePrinter, TaskFileReader}

class FuzzyRelationOperationsSolver extends Solver {
  val fuzzyRelationService = new DefaultFuzzyRelationService
  val taskFileReader = new TaskFileReader

  override def solve(): Unit = {
    val relations = taskFileReader.parseFuzzyRelations("src/main/resources/cp6/fuzzy_relations.txt")
    val r1 = relations.head
    val r2 = relations.tail.head

    print("R1: ")
    ConsolePrinter.printMatrix(relations.head.matrix)

    print("R2: ")
    ConsolePrinter.printMatrix(relations(1).matrix)

    println("Analyze properties for R1")

    ConsolePrinter.printRelationProperties(fuzzyRelationService.findPropertiesForRelation(relations.head))
    ConsolePrinter.printRelationPropertyViolations(fuzzyRelationService.getPropertiesCheckResults(relations.head).map(_.propertyViolation))

    println("Union R1 and R2:")
    ConsolePrinter.printMatrix(r1.union(r2).matrix)
    println("Intersect R1 and R2:")
    ConsolePrinter.printMatrix(r1.intersect(r2).matrix)
    println("Complement R1:")
    ConsolePrinter.printMatrix(r1.complement().matrix)
    println("Complement R2:")
    ConsolePrinter.printMatrix(r2.complement().matrix)
    println("Compose R1 and R2:")
    ConsolePrinter.printMatrix(r1.compose(r2).matrix)
    println("Alpha level 0.5 for R1:")
    ConsolePrinter.printRelationMatrix(r1.alphaLevel(0.5))
    println("Converse R1:")
    ConsolePrinter.printMatrix(r1.converse().matrix)
    println("Strict preference relation for R1:")
    ConsolePrinter.printMatrix(fuzzyRelationService.getStrictPreference(r1).matrix)
  }
}
