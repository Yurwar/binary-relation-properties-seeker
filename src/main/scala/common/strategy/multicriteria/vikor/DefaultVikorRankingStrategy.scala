package com.yurwar
package common.strategy.multicriteria.vikor

import common.entity.VikorCriteria

class DefaultVikorRankingStrategy extends VikorRankingStrategy {


  override def findRankedAlternatives(criteria: VikorCriteria): List[Int] = {
    val desirableValues = findDesiredValues(criteria.alternativesRating.map(_.map(_.toDouble)), criteria.criterionTypes)
    val worstValues: List[Double] = findWorstValues(criteria.alternativesRating.map(_.map(_.toDouble)), criteria.criterionTypes)

    val normalizedWeights: List[Double] = normalizeWeights(criteria.weights)

    val avgImprovementInterval: List[Double] = calculateAvgImprovementIntervals(criteria.alternativesRating,
      normalizedWeights, desirableValues, worstValues)
    println(s"S: ${avgImprovementInterval.map("%.3f".format(_)).mkString(", ")}")
    val maxImprovementIntervals: List[Double] = calculateMaxImprovementIntervals(criteria.alternativesRating,
      normalizedWeights, desirableValues, worstValues)
    println(s"R: ${maxImprovementIntervals.map("%.3f".format(_)).mkString(", ")}")

    val proximityDegrees = calculateProximityDegrees(criteria.strategyWeight,
      avgImprovementInterval,
      maxImprovementIntervals)
    println(s"Q: ${proximityDegrees.map("%.3f".format(_)).mkString(", ")}")


    val sSorted = avgImprovementInterval.zipWithIndex.sortBy(_._1)
    println(s"S sorted: ${sSorted.map(tup => "x" + (tup._2 + 1) + " = " + "%.3f".format(tup._1)).mkString(" > ")}")

    val rSorted = maxImprovementIntervals.zipWithIndex.sortBy(_._1)
    println(s"R sorted: ${rSorted.map(tup => "x" + (tup._2 + 1) + " = " + "%.3f".format(tup._1)).mkString(" > ")}")

    val qSorted = proximityDegrees.zipWithIndex.sortBy(_._1)
    println(s"Q sorted: ${qSorted.map(tup => "x" + (tup._2 + 1) + " = " + "%.3f".format(tup._1)).mkString(" > ")}")

    if (checkAcceptableAdvantage(qSorted.head._1, qSorted(1)._1, criteria.alternativesRating.size) &&
      checkAcceptableStability(qSorted.head._2, sSorted, rSorted)) {
      List(qSorted.head._2)
    } else if (checkAcceptableAdvantage(qSorted.head._1, qSorted(1)._1, criteria.alternativesRating.size)) {
      List(qSorted.head._2, qSorted(1)._2)
    } else {
      val idx = findSuitableAlternativeIdx(qSorted, criteria.alternativesRating.size)
      qSorted.take(idx + 1).map(_._2)
    }
  }

  def findSuitableAlternativeIdx(qSorted: List[(Double, Int)], n: Int): Int = {
    var k = 1
    while ((qSorted(k)._1 - qSorted.head._1) >= 1 / (n.toDouble - 1)) {
      k += 1
    }
    k
  }

  def checkAcceptableAdvantage(q1: Double, q2: Double, n: Int): Boolean = {
    q2 - q1 >= (1 / (n.toDouble - 1))
  }

  def checkAcceptableStability(aIdx: Int, sSorted: List[(Double, Int)], rSorted: List[(Double, Int)]): Boolean = {
    aIdx == sSorted.head._2 || aIdx == rSorted.head._2
  }

  def calculateProximityDegrees(strategyWeight: Double,
                                avgImprovementInterval: List[Double],
                                maxImprovementIntervals: List[Double]): List[Double] = {
    val minS = avgImprovementInterval.min
    val maxS = avgImprovementInterval.max

    val minR = maxImprovementIntervals.min
    val maxR = maxImprovementIntervals.max

    avgImprovementInterval.zip(maxImprovementIntervals)
      .map(srIntervals => {
        val s = srIntervals._1
        val r = srIntervals._2

        strategyWeight * (s - minS) / (maxS - minS) +
          (1 - strategyWeight) * (r - minR) / (maxR - minR)
      })
  }

  private def normalizeWeights(weights: List[Double]): List[Double] = {
    val wSum = weights.sum

    weights.map(_ / wSum)
  }

  def calculateAvgImprovementIntervals(alternativesRating: List[List[Int]],
                                       normalizedWeights: List[Double],
                                       desirableValues: List[Double],
                                       worstValues: List[Double]): List[Double] = {
    alternativesRating.indices.map(row => alternativesRating.head.indices.map(col => {
      normalizedWeights(col) *
        (desirableValues(col) - alternativesRating(row)(col)) /
        (desirableValues(col) - worstValues(col))
    }).sum).toList
  }

  def calculateMaxImprovementIntervals(alternativesRating: List[List[Int]],
                                       normalizedWeights: List[Double],
                                       desirableValues: List[Double],
                                       worstValues: List[Double]): List[Double] = {
    alternativesRating.indices.map(row => alternativesRating.head.indices.map(col => {
      normalizedWeights(col) *
        (desirableValues(col) - alternativesRating(row)(col)) /
        (desirableValues(col) - worstValues(col))
    }).max).toList
  }
}
