package com.yurwar
package common.service

import common.entity.RelationProperty.{AntiReflexivity, AntiSymmetry, Asymmetry, Reflexivity, RelationProperty, StrongAntiReflexivity, StrongConnectivity, StrongReflexivity, Symmetry, Transitivity, WeakAntiReflexivity, WeakConnectivity, WeakReflexivity}
import common.entity.{FuzzyRelation, PropertyCheckResult}
import common.strategy.property.fuzzy._
import common.strategy.property.fuzzy.impl.antireflexivity._
import common.strategy.property.fuzzy.impl.connectivity._
import common.strategy.property.fuzzy.impl.reflexivity._
import common.strategy.property.fuzzy.impl.symmetry._
import common.strategy.property.fuzzy.impl.transitivity._

import scala.collection.mutable.ListBuffer

class DefaultFuzzyRelationService extends FuzzyRelationService {
  val FuzzyPropertyStrategies: Map[RelationProperty, FuzzyPropertyCheckStrategy] = Map(
    (AntiReflexivity, new AntiReflexivityCheckStrategy),
    (StrongAntiReflexivity, new StrongAntiReflexivityCheckStrategy),
    (WeakAntiReflexivity, new WeakAntiReflexivityCheckStrategy),
    (StrongConnectivity, new StrongConnectivityCheckStrategy),
    (WeakConnectivity, new WeakConnectivityCheckStrategy),
    (Reflexivity, new ReflexivityCheckStrategy),
    (StrongReflexivity, new StrongReflexivityCheckStrategy),
    (WeakReflexivity, new WeakReflexivityCheckStrategy),
    (AntiSymmetry, new AntiSymmetryCheckStrategy),
    (Asymmetry, new AsymmetryCheckStrategy),
    (Symmetry, new SymmetryCheckStrategy),
    (Transitivity, new TransitivityCheckStrategy)
  )

  override def getStrictPreference(relation: FuzzyRelation): FuzzyRelation = {
    val conversedRelation = relation.converse()

    FuzzyRelation(relation.matrix.zip(conversedRelation.matrix)
      .map(rowTup => rowTup._1.zip(rowTup._2))
      .map(row => row.map(pair => {
        if (pair._1 > pair._2) {
          pair._1 - pair._2
        } else {
          0
        }
      })))
  }

  override def findPropertiesForRelation(relation: FuzzyRelation): List[RelationProperty] = {
    val properties = new ListBuffer[RelationProperty]

    FuzzyPropertyStrategies.foreach(entry => {
      if (entry._2.check(relation).present) {
        properties.addOne(entry._1)
      }
    })

    properties.toList
  }

  override def getPropertiesCheckResults(relation: FuzzyRelation): List[PropertyCheckResult] = {
    FuzzyPropertyStrategies.map(entry => {
      entry._2.check(relation)
    }).toList
  }

  override def findPreferableAlternatives(relation: FuzzyRelation): List[Int] = {
    ???
  }
}
