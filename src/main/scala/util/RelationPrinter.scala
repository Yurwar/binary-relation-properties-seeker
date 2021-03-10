package com.yurwar
package util

import entity.Relation

object RelationPrinter {

  def printRelation(relation: Relation): Unit = {
    println("Relation:")
    for (i <- relation.matrix.indices) {
      for (j <- relation.matrix(i).indices) {
        print((if (relation.matrix(i)(j)) 1 else 0) + " ")
      }
      println
    }

    println

    print("Relation Properties: ")
    println(relation.relationProperties.mkString(", "))

    println

    print("Relation Class: ")
    println(relation.relationClass)

    println

    relation.propertyViolations.foreach(v => {
      println(s"Violation for property ${v.relationProperty} in points: ${v.violationPoints.map(t => (t._1 + 1, t._2 + 1)).mkString(", ")}")
    })

    println
  }

}
