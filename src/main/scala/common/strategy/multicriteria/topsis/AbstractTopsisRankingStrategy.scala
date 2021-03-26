package com.yurwar
package common.strategy.multicriteria.topsis

import common.entity.{CriterionType, TopsisCriteria}
import common.util.ConsolePrinter

abstract class AbstractTopsisRankingStrategy extends TopsisRankingStrategy {

  override def findRankedAlternatives(criteria: TopsisCriteria): List[Int] = {
    val normalizedRating = normalizeRating(criteria.alternativesRating.map(_.map(_.toDouble)), criteria.criterionTypes)

    println()
    println("Normalized rating: ")
    ConsolePrinter.printMatrix(normalizedRating)

    val weightRating = weighRating(normalizedRating, criteria.weights)

    println()
    println("Weight rating: ")
    ConsolePrinter.printMatrix(weightRating)

    val pis = findDesiredValues(weightRating, criteria.criterionTypes)

    println()
    println(s"PIS ${pis.map("%.3f".format(_)).mkString(", ")}")

    val nis = findWorstValues(weightRating, criteria.criterionTypes)

    println()
    println(s"NIS ${nis.map("%.3f".format(_)).mkString(", ")}")

    val distancesToPis = calculateDistances(weightRating, pis)

    println()
    println(s"Distances to PIS ${distancesToPis.map("%.3f".format(_)).mkString(", ")}")

    val distancesToNis = calculateDistances(weightRating, nis)

    println(s"Distances to NIS ${distancesToNis.map("%.3f".format(_)).mkString(", ")}")

    val closeness = calculateCloseness(distancesToPis, distancesToNis)

    println()
    println(s"Closeness ${closeness.map("%.3f".format(_)).mkString(", ")}")

    closeness.zipWithIndex.sortBy(_._1).reverse.map(_._2)
  }

  def calculateCloseness(distancesToPis: List[Double], distancesToNis: List[Double]): List[Double] = {
    distancesToPis.zip(distancesToNis)
      .map(dp2dn => dp2dn._2 / (dp2dn._1 + dp2dn._2))
  }

  private def calculateDistances(weightRating: List[List[Double]], point: List[Double]): List[Double] = {
    weightRating.map(alternativePoint => {
      math.sqrt(alternativePoint.zip(point)
        .map(tup => math.pow(tup._1 - tup._2, 2))
        .sum)
    })
  }

  private def weighRating(normalizedRating: List[List[Double]], weight: List[Double]): List[List[Double]] = {
    normalizedRating.indices.map(row => {
      normalizedRating.head.indices.map(col => {
        normalizedRating(row)(col) * weight(col)
      }).toList
    }).toList
  }

  protected def normalizeRating(alternativesRating: List[List[Double]], criterionTypes: List[CriterionType]): List[List[Double]]

}
