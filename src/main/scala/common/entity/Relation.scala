package com.yurwar
package common.entity

import common.entity.RelationClasses.RelationClass
import common.entity.RelationProperty.RelationProperty

import scala.collection.mutable.ListBuffer


class Relation(var matrix: List[List[Boolean]],
               var relationClass: RelationClass = RelationClasses.Undefined,
               val relationProperties: ListBuffer[RelationProperty] = ListBuffer.empty,
               val propertyViolations: ListBuffer[PropertyViolation] = ListBuffer.empty) {

  val size: Int = {
    matrix.size
  }

  def invertedMatrix: List[List[Boolean]] = {
    matrix.map(_.map {
      case true => false
      case false => true
    })
  }

  def getLowerSection(element: Int): List[Int] = {
    matrix.indices
      .filter(idx => getElement(element, idx))
      .toList
  }

  def getElement(row: Int, column: Int): Boolean = {
    matrix(row)(column)
  }

  def getUpperSection(element: Int): List[Int] = {
    matrix.indices
      .filter(idx => getElement(idx, element))
      .toList
  }

  def negateConnection(row: Int, column: Int): Unit = {
    val negated = !getElement(row, column)
    matrix = matrix.updated(row, matrix(row).updated(column, negated))
  }

  override def toString: String = s"Relation(matrix=$matrix, " +
    s"relationClass=$relationClass, " +
    s"relationProperties=$relationProperties)"
}
