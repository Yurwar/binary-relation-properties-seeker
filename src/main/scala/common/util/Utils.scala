package com.yurwar
package common.util

import common.entity.RelationProperty._
import common.strategy.optimization.OptimizationStrategy
import common.strategy.optimization.impl.blocking._
import common.strategy.optimization.impl.domination._
import common.strategy.optimization.impl.k.{AbstractKOptimizationStrategy, KMaxOptimizationStrategy, KOptOptimizationStrategy}
import common.strategy.optimization.impl.nm.NmOptimizationStrategy
import common.strategy.property.PropertyCheckStrategy
import common.strategy.property.impl._

import scala.collection.mutable

object Utils {
  val PropertyStrategies: Map[Value, PropertyCheckStrategy] = Map(
    (Acyclicity, new AcyclicityCheckStrategy),
    (AntiReflexivity, new AntiReflexivityCheckStrategy),
    (AntiSymmetry, new AntiSymmetryCheckStrategy),
    (Asymmetry, new AsymmetryCheckStrategy),
    (Connectivity, new ConnectivityCheckStrategy),
    (NegativeTransitivity, new NegativelyTransitivityCheckStrategy),
    (Reflexivity, new ReflexivityCheckStrategy),
    (Symmetry, new SymmetryCheckStrategy),
    (Transitivity, new TransitivityCheckStrategy)
  )

  val DominationBlockingStrategies: mutable.LinkedHashMap[String, OptimizationStrategy] = mutable.LinkedHashMap(
    "X*P" -> new AsymmetryDominationOptimizationStrategy,
    "X*R" -> new DominationOptimizationStrategy,
    "X**R" -> new StrictDominationOptimizationStrategy,
    "X0P" -> new AsymmetryBlockingOptimizationStrategy,
    "X0R" -> new BlockingOptimizationStrategy,
    "X00R" -> new StrictBlockingOptimizationStrategy,
  )

  val NmKOptimizationStrategies: mutable.LinkedHashMap[String, OptimizationStrategy] = mutable.LinkedHashMap(
    "NM Optimization" -> new NmOptimizationStrategy(),
    "K1 Max Optimization" -> new KMaxOptimizationStrategy(1),
    "K1 Opt Optimization" -> new KOptOptimizationStrategy(1),
    "K2 Max Optimization" -> new KMaxOptimizationStrategy(2),
    "K2 Opt Optimization" -> new KOptOptimizationStrategy(2),
    "K3 Max Optimization" -> new KMaxOptimizationStrategy(3),
    "K3 Opt Optimization" -> new KOptOptimizationStrategy(3),
    "K4 Max Optimization" -> new KMaxOptimizationStrategy(4),
    "K4 Opt Optimization" -> new KOptOptimizationStrategy(4),
  )
}
