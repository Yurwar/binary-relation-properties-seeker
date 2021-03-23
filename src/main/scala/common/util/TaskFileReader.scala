package com.yurwar
package common.util

import common.entity.{ElectreCriteria, Relation, SimpleCriteria}

import scala.io.Source

class TaskFileReader {
  private val RAW_CONTENT_SPLIT_REGEX = ".*-+\\r?\\n"
  private val LINE_SPLIT_REGEX = "\\r?\\n"
  private val SPACE_SPLIT_REGEX = "\\s{2}"

  def parseRelations(fileName: String): List[Relation] = {
    val rawContent = readFile(fileName)

    val rawMatrices = List.from(rawContent.split(RAW_CONTENT_SPLIT_REGEX))
      .filter(str => !str.isBlank)


    rawMatrices.map(rawMatrix => {
      val lines = rawMatrix.split(LINE_SPLIT_REGEX)
      lines.map(line => {

        line.trim.split(SPACE_SPLIT_REGEX).map {
          case "0" => false
          case "1" => true
        }.toList
      }).toList
    }).map(matrix => new Relation(matrix))
  }

  def parseCriteria(fileName: String): SimpleCriteria = {
    val criteriaRawParts = readFile(fileName)
      .split("\\r?\\n\\r?\\n")

    val criteriaRating = extractCriteriaRating(criteriaRawParts)

    val criteriaOrder = criteriaRawParts(1)
      .split(" ?> ?")
      .map(criterion => criterion.split("")(1))
      .map(_.toInt)
      .map(criterionNumber => criterionNumber - 1)
      .toList

    val criteriaGroupOrder = criteriaRawParts(2)
      .split(" ?< ?")
      .map(equivalenceGroup => {
        equivalenceGroup.substring(1, equivalenceGroup.length - 1)
          .split(",")
          .map(criterion => criterion.split("")(1))
          .map(_.toInt)
          .map(criterionNumber => criterionNumber - 1)
          .toList
      }).toList

    new SimpleCriteria(criteriaRating, criteriaOrder, criteriaGroupOrder)
  }

  private def extractCriteriaRating(criteriaRawParts: Array[String]) = {
    val criteriaRating = criteriaRawParts(0)
      .split(LINE_SPLIT_REGEX)
      .map(criterionRating => {
        criterionRating.trim.split(" ")
          .filter(_.nonEmpty)
          .map(_.toInt).toList
      }).toList
    criteriaRating
  }

  def parseElectreCriteria(fileName: String): ElectreCriteria = {
    val criteriaRawParts = readFile(fileName)
      .split("\\r?\\n\\r?\\n")

    val criteriaRating = extractCriteriaRating(criteriaRawParts)

    val criteriaWeights = criteriaRawParts(1)
      .split(" ")
      .filter(_.nonEmpty)
      .map(_.toInt)
      .toList

    val thresholds = criteriaRawParts(2)
      .split(" ")
      .filter(_.nonEmpty)
      .map(_.toDouble)

    ElectreCriteria(criteriaRating, criteriaWeights, thresholds(0), thresholds(1))
  }

  private def readFile(filename: String): String = {
    val fileSource = Source.fromFile(filename)
    val rawContent = fileSource.mkString
    fileSource.close()

    rawContent
  }
}
