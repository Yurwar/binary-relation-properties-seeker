package com.yurwar
package cp1.solver

import common.entity.Relation
import common.entity.RelationClasses.RelationClass
import common.strategy.property.PropertyCheckStrategy
import common.util.{RelationPrinter, Utils}

abstract class AbstractRestoreSolver extends Solver {

  def restoreClassForRelation(relation: Relation, relationClass: RelationClass): Unit = {
    relationClass.props.foreach(prop => {
      val propertyCheckStrategy = Utils.PropertyStrategies(prop)
      println(s"Start restoring property $prop for relation")
      restorePropertyForRelation(relation, propertyCheckStrategy)
      println(s"After restoring $prop")
      RelationPrinter.printRelationMatrix(relation)
    })
  }

  def restorePropertyForRelation(relation: Relation, propertyCheckStrategy: PropertyCheckStrategy): Unit = {
    var checkResult = propertyCheckStrategy.check(relation)

    while (!checkResult.present) {
      val head = checkResult.propertyViolation.violationPoints.head
      relation.negateConnection(head._1, head._2)
      checkResult = propertyCheckStrategy.check(relation)
    }
  }

}
