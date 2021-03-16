package com.yurwar
package common.util

import common.entity.Relation

import scala.io.Source

class RelationReader {
  private val RAW_CONTENT_SPLIT_REGEX = "№[0-9]* -{13}\\r?\\n"
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

  def readFile(filename: String): String = {
    val fileSource = Source.fromFile(filename)
    val rawContent = fileSource.mkString
    fileSource.close()

    rawContent
  }
}
