package com.yurwar
package common.entity

object RelationProperty extends Enumeration {
  type RelationProperty = Value

  val Reflexivity, AntiReflexivity, Symmetry,
  Asymmetry, AntiSymmetry, Transitivity,
  NegativeTransitivity, Acyclicity, Connectivity = Value
}
