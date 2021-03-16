package com.yurwar
package common.strategy.property.impl

import common.entity.RelationProperty.Connectivity
import common.entity.{PropertyCheckResult, PropertyViolation, Relation}
import common.strategy.property.PropertyCheckStrategy

class ConnectivityCheckStrategy extends PropertyCheckStrategy {
  val reflexivityCheckStrategy: ReflexivityCheckStrategy = new ReflexivityCheckStrategy

  override def check(relation: Relation): PropertyCheckResult = {
    val maybeZero = findSymmetricZero(relation)
    if (maybeZero.isDefined) {
      new PropertyCheckResult(false, new PropertyViolation(Connectivity, List(maybeZero.get)))
    } else {
      val reflexivityRes = reflexivityCheckStrategy.check(relation)
      new PropertyCheckResult(reflexivityRes.present, new PropertyViolation(Connectivity, reflexivityRes.propertyViolation.violationPoints))
    }

  }

  private def findSymmetricZero(relation: Relation): Option[(Int, Int)] = {
    for (i <- 0 until relation.size;
         j <- i + 1 until relation.size) {
      val upperRelation = relation.getElement(i, j)
      val lowerRelation = relation.getElement(j, i)

      if (!upperRelation && !lowerRelation)
        return Option((i, j))
    }

    Option.empty
  }

}
