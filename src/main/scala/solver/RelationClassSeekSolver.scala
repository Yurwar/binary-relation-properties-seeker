package com.yurwar
package solver

import entity.{Relation, RelationClasses, RelationProperty}
import strategy.property.PropertyCheckStrategy
import strategy.property.impl._
import util.{RelationPrinter, RelationReader}

class RelationClassSeekSolver extends Solver {
  val taskFileName = "src/main/resources/binary_relations_task1.txt"
  val propertyStrategies: List[(RelationProperty.Value, PropertyCheckStrategy)] = List(
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


  override def solve(): Unit = {
    val a: RelationReader = new RelationReader

    val relations = a.parseRelations(taskFileName)

    runPropertyCheckStrategies(relations)

    findClassForRelation(relations)

    relations.foreach(RelationPrinter.printRelation)
  }

  private def findClassForRelation(relations: List[Relation]): Unit = {
    relations.foreach(rel => {
      RelationClasses.values.toList.sortBy(_.props.size).foreach(rc => {
        if (rc.props.forall(rel.relationProperties.contains)) {
          rel.relationClass = rc
        }
      })
    })
  }

  private def runPropertyCheckStrategies(relations: List[Relation]): Unit = {
    relations.foreach(rel => {
      propertyStrategies.foreach(propStrategy => {
        val checkResult = propStrategy._2.check(rel)
        if (checkResult.present) {
          rel.relationProperties.addOne(propStrategy._1)
        } else {
          rel.propertyViolations.addOne(checkResult.propertyViolation)
        }
      })
    })
  }
}
