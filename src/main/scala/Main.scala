package com.yurwar

import entity.{RelationClasses, RelationProperty}
import strategy.property.PropertyCheckStrategy
import strategy.property.impl._
import util.{RelationPrinter, RelationReader}

object Main {
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

  def main(args: Array[String]): Unit = {
    val a: RelationReader = new RelationReader

    val relations = a.parseRelations()

    relations.foreach(rel => {
      propertyStrategies.foreach(propStrategy => {
        if (propStrategy._2.hasProperty(rel)) {
          rel.relationProperties.addOne(propStrategy._1)
        }
      })
    })

    relations.foreach(rel => {
      RelationClasses.values.toList.sortBy(_.props.size).foreach(rc => {
        if (rc.props.forall(rel.relationProperties.contains)) {
          rel.relationClass = rc
        }
      })
    })

    relations.foreach(RelationPrinter.printRelation)
  }
}
