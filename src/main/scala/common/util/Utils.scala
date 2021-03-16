package com.yurwar
package common.util

import common.entity.RelationProperty
import common.strategy.property.PropertyCheckStrategy
import common.strategy.property.impl._

object Utils {
  val PropertyStrategies: Map[RelationProperty.Value, PropertyCheckStrategy] = Map(
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

}
