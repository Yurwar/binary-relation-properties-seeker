package com.yurwar
package entity

import entity.RelationProperty.{AntiReflexivity, AntiSymmetry, Asymmetry, NegativeTransitivity, Reflexivity, RelationProperty, Symmetry, Transitivity}

object RelationClasses extends Enumeration {
  type RelationClass = Value

  val Undefined = RelationClassProperties(List.empty)
  val Equivalence = RelationClassProperties(List(Reflexivity, Symmetry, Transitivity))
  val StrictOrder = RelationClassProperties(List(AntiReflexivity, Asymmetry, Transitivity))
  val NotStrictOrder = RelationClassProperties(List(Reflexivity, AntiSymmetry, Transitivity))
  val QuasiOrder = RelationClassProperties(List(Reflexivity, Transitivity))
  val WeakOrdering = RelationClassProperties(List(Asymmetry, Transitivity, NegativeTransitivity))
  val Tolerance = RelationClassProperties(List(Reflexivity, Symmetry))

  protected case class RelationClassProperties(props: List[RelationProperty])
    extends super.Val {

  }

  implicit def valueToRelationClassProperties(x: Value): RelationClassProperties = {
    x.asInstanceOf[RelationClassProperties]
  }
}
