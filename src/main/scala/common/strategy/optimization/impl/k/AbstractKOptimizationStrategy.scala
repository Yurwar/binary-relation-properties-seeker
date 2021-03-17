package com.yurwar
package common.strategy.optimization.impl.k

import common.entity.Relation
import common.entity.RelationProperty.Acyclicity
import common.strategy.optimization.AbstractOptimizationStrategy

abstract class AbstractKOptimizationStrategy(k: Int) extends AbstractOptimizationStrategy(Set.empty, Set(Acyclicity)) {
  val P = "P"
  val I = "I"
  val N = "N"
  val Zero = "ZERO"

  val ElementTypeToProperty: Map[(Boolean, Boolean) => Boolean, String] = Map(
    ((a: Boolean, b: Boolean) => a && !b) -> P,
    ((a: Boolean, b: Boolean) => a && b) -> I,
    ((a: Boolean, b: Boolean) => !a && !b) -> N,
    ((a: Boolean, b: Boolean) => !a && b) -> Zero,
  )

  val KNumToTypes: Map[Int, Set[String]] = Map(
    1 -> Set(P, I, N),
    2 -> Set(P, N),
    3 -> Set(P, I),
    4 -> Set(P),
  )

  override def getOptimalElements(rel: Relation): List[Int] = {
    getOptimizedForK(convertBooleanToTypeMatrix(rel.matrix), KNumToTypes(k))
  }

  private def convertBooleanToTypeMatrix(matrix: List[List[Boolean]]): List[List[String]] = {
    matrix.indices.map(rowIdx => {
      matrix.indices.map(columnIdx => {
        ElementTypeToProperty
          .find(tup => tup._1(matrix(rowIdx)(columnIdx), matrix(columnIdx)(rowIdx)))
          .map(_._2)
          .getOrElse(Zero)
      }).toList
    }).toList
  }

  private def getOptimizedForK(matrix: List[List[String]], types: Set[String]): List[Int] = {
    val sections = matrix.map(row => {
      matrix.indices
        .filter(columnIdx => types.contains(row(columnIdx)))
        .toList
    })

    filterCriteriaMatchedElements(sections)
  }

  protected def filterCriteriaMatchedElements(sections: List[List[Int]]): List[Int]
}
