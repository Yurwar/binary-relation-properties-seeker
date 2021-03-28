package com.yurwar
package common.entity

object RelationProperty extends Enumeration {
  type RelationProperty = Value

  val Reflexivity, StrongReflexivity, WeakReflexivity,
  AntiReflexivity, StrongAntiReflexivity, WeakAntiReflexivity,
  Symmetry, Asymmetry, AntiSymmetry,
  Transitivity, NegativeTransitivity, Acyclicity,
  Connectivity, StrongConnectivity, WeakConnectivity = Value
}
