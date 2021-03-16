package com.yurwar
package common.strategy.property.impl

import common.entity.{PropertyCheckResult, PropertyViolation, Relation, RelationProperty}
import common.strategy.property.PropertyCheckStrategy

import scala.collection.mutable.ListBuffer

class AcyclicityCheckStrategy extends PropertyCheckStrategy {

  override def check(relation: Relation): PropertyCheckResult = {
    val matrixAdj = relation.matrix.indices.map(idx => relation.getLowerSection(idx)).toList

    //TODO Populate violations for acyclicity
    new PropertyCheckResult(!isCyclic(matrixAdj), new PropertyViolation(RelationProperty.Acyclicity, List.empty))
  }

  def isCyclic(matrix: List[List[Int]]): Boolean = {
    def isCyclicNested(el: Int, matrix: List[List[Int]], visited: ListBuffer[Boolean], recStack: ListBuffer[Boolean]): Boolean = {
      if (recStack(el))
        return true

      if (visited(el))
        return false

      visited(el) = true
      recStack(el) = true

      val children = matrix(el)

      for (child <- children) {
        if (isCyclicNested(child, matrix, visited, recStack))
          return true
      }

      recStack(el) = false

      false
    }

    val visited: ListBuffer[Boolean] = createEmptyList(matrix.size)
    val recStack: ListBuffer[Boolean] = createEmptyList(matrix.size)

    for (el <- matrix.indices) {
      if (isCyclicNested(el, matrix, visited, recStack))
        return true
    }

    false
  }

  def createEmptyList(size: Int): ListBuffer[Boolean] = {
    val res: ListBuffer[Boolean] = ListBuffer.empty

    for (_ <- 0 until size)
      res.addOne(false)

    res
  }
}
