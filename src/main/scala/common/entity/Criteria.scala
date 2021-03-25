package com.yurwar
package common.entity

sealed abstract class Criteria(val alternativesRating: List[List[Int]])

sealed case class SimpleCriteria(override val alternativesRating: List[List[Int]],
                                 strictDescCriterionOrder: List[Int],
                                 quasiAscCriterionGroupOrder: List[List[Int]]) extends Criteria(alternativesRating)

sealed class MultiCriteria(override val alternativesRating: List[List[Int]],
                           val weights: List[Double]) extends Criteria(alternativesRating)

sealed case class ElectreCriteria(override val alternativesRating: List[List[Int]],
                                  override val weights: List[Double],
                                  concordanceThreshold: Double,
                                  discordanceThreshold: Double) extends MultiCriteria(alternativesRating, weights)

sealed case class TopsisCriteria(override val alternativesRating: List[List[Int]],
                                 override val weights: List[Double],
                                 criterionTypes: List[CriterionType]) extends MultiCriteria(alternativesRating, weights)
