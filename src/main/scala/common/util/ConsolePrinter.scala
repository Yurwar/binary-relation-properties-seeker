package com.yurwar
package common.util

import common.entity.{Criteria, MultiCriteria, Relation, TopsisCriteria}

object ConsolePrinter {

  def printRelation(relation: Relation): Unit = {
    printRelationMatrix(relation)

    printRelationProperties(relation)

    printRelationClass(relation)

    printRelationPropertyViolations(relation)
  }

  def printRelationMatrix(relation: Relation): Unit = {
    println("Relation:")
    for (i <- relation.matrix.indices) {
      for (j <- relation.matrix(i).indices) {
        print((if (relation.matrix(i)(j)) 1 else 0) + " ")
      }
      println
    }

    println
  }

  def printRelationProperties(relation: Relation): Unit = {
    print("Relation Properties: ")
    println(relation.relationProperties.mkString(", "))

    println
  }

  def printRelationClass(relation: Relation): Unit = {
    print("Relation Class: ")
    println(relation.relationClass)

    println
  }

  def printRelationPropertyViolations(relation: Relation): Unit = {
    relation.propertyViolations.foreach(v => {
      println(s"Violation for property ${v.relationProperty} in points: ${v.violationPoints.map(t => (t._1 + 1, t._2 + 1)).mkString(", ")}")
    })

    println
  }

  def printCriteriaRating(criteria: Criteria): Unit = {
    println("Alternatives rating: ")

    for (i <- criteria.alternativesRating.indices) {
      println(criteria.alternativesRating(i).mkString(" "))
    }

    println
  }

  def printCriteriaWeights(criteria: MultiCriteria): Unit = {
    println(s"Weights: (${criteria.weights.mkString(", ")})")
  }

  def printCriteriaTypes(criteria: TopsisCriteria): Unit = {
    println(s"Types: (${criteria.criterionTypes.mkString(", ")})")
  }

  def printIndexMatrix(idxType: String, index: List[List[Double]]): Unit = {
    println(idxType + " index:")

    for (i <- index.indices) {
      println(index(i).map("%.3f".format(_)).mkString(" "))
    }

    println()
  }

  def printMatrix(matrix: List[List[Double]]): Unit = {
    println()

    for (i <- matrix.indices) {
      println(matrix(i).map("%.3f".format(_)).mkString(" "))
    }

    println()
  }
}
