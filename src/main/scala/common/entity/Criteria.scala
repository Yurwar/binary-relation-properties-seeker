package com.yurwar
package common.entity

sealed abstract class Criteria(val alternativesRating: List[List[Int]])

sealed case class SimpleCriteria(override val alternativesRating: List[List[Int]],
                                 strictDescCriterionOrder: List[Int],
                                 quasiAscCriterionGroupOrder: List[List[Int]]) extends Criteria(alternativesRating)

sealed case class ElectreCriteria(override val alternativesRating: List[List[Int]],
                                  weights: List[Int],
                                  concordanceThreshold: Double,
                                  discordanceThreshold: Double) extends Criteria(alternativesRating)
