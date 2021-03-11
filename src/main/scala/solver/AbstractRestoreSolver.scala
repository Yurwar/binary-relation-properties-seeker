package com.yurwar
package solver

import entity.RelationClasses.RelationClass
import entity.{Relation, RelationProperty}
import strategy.property.PropertyCheckStrategy
import strategy.property.impl._
import util.RelationPrinter

abstract class AbstractRestoreSolver extends Solver {
  val propertyStrategies: Map[RelationProperty.Value, PropertyCheckStrategy] = Map(
    (RelationProperty.Acyclicity, new AcyclicityCheckStrategy),
    (RelationProperty.AntiReflexivity, new AntiReflexivityCheckStrategy),
    (RelationProperty.AntiSymmetry, new AntiSymmetryCheckStrategy),
    (RelationProperty.Asymmetry, new AsymmetryCheckStrategy),
    (RelationProperty.Connectivity, new ConnectivityCheckStrategy),
    (RelationProperty.NegativeTransitivity, new NegativelyTransitivityCheckStrategy),
    (RelationProperty.Reflexivity, new ReflexivityCheckStrategy),
    (RelationProperty.Symmetry, new SymmetryCheckStrategy),
    (RelationProperty.Transitivity, new TransitivityCheckStrategy)
  )

  def restoreClassForRelation(relation: Relation, relationClass: RelationClass): Unit = {
    relationClass.props.foreach(prop => {
      val propertyCheckStrategy = propertyStrategies(prop)
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
