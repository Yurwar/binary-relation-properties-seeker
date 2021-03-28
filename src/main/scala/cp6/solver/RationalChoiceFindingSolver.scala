package com.yurwar
package cp6.solver

import common.service.DefaultFuzzyRelationService
import common.util.{ConsolePrinter, TaskFileReader}

class RationalChoiceFindingSolver extends Solver {
  val fuzzyRelationService = new DefaultFuzzyRelationService
  val taskFileReader = new TaskFileReader

  override def solve(): Unit = {
    val relations = taskFileReader.parseFuzzyRelations("src/main/resources/cp6/fuzzy_relations.txt")
    val r1 = relations.head

    print("R1: ")
    ConsolePrinter.printMatrix(r1.matrix)

    println("Strict preference relation for R1:")
    val strictPreference = fuzzyRelationService.getStrictPreference(r1).matrix
    ConsolePrinter.printMatrix(strictPreference)

    println("Max level of dominating for alternatives by others for R1:")
    val maxElements = strictPreference.indices.map(col => {
      strictPreference.indices
        .map(idx => strictPreference(idx)(col)).max
    })
    println(maxElements.map("%.3f".format(_)).mkString(", "))
    println("Level of not domination for alternatives by others for R1:")
    val invertedMaxElements = maxElements.map(1 - _)
    println(invertedMaxElements.map("%.3f".format(_)).mkString(", "))
    println("Best alternatives:")
    val maxValueForInverted = invertedMaxElements.max
    val bestAlternatives = invertedMaxElements.zipWithIndex.filter(tup => tup._1 == maxValueForInverted)
      .map(_._2)
    println(bestAlternatives.map(el => s"x${el + 1}").mkString(", "))
  }
}
