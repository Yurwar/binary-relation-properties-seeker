package com.yurwar
package util

import entity.Relation

import scala.io.Source

class RelationReader {
  val RAW_CONTENT_SPLIT_REGEX = "№[0-9]* -{13}\\r?\\n"
  val LINE_SPLIT_REGEX = "\\r?\\n"
  val SPACE_SPLIT_REGEX = "\\s{2}"

  def parseRelations(): List[Relation] = {
    val rawContent = readFile("src/main/resources/binary_relations_task1.txt")

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
