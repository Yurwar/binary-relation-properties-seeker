package com.yurwar
package common.util

import common.entity.Relation

object RelationPrinter {

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
}
