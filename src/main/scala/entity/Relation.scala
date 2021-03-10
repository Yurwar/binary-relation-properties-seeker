package com.yurwar
package entity

import entity.RelationClasses.RelationClass
import entity.RelationProperty.RelationProperty

import scala.collection.mutable.ListBuffer


class Relation(val matrix: List[List[Boolean]],
               var relationClass: RelationClass = RelationClasses.Undefined,
               val relationProperties: ListBuffer[RelationProperty] = ListBuffer.empty,
               val propertyViolations: ListBuffer[PropertyViolation] = ListBuffer.empty) {

  val size: Int = {
    matrix.size
  }

  val invertedMatrix: List[List[Boolean]] = {
    matrix.map(_.map {
      case true => false
      case false => true
    })
  }

  def getElement(row: Int, column: Int): Boolean = {
    matrix(row)(column)
  }

  def getLowerSection(element: Int): List[Int] = {
    matrix.indices
      .filter(idx => getElement(element, idx))
      .toList
  }

  def getUpperSection(element: Int): List[Int] = {
    matrix.indices
      .filter(idx => getElement(idx, element))
      .toList
  }

  override def toString: String = s"Relation(matrix=$matrix, " +
    s"relationClass=$relationClass, " +
    s"relationProperties=$relationProperties)"
}
