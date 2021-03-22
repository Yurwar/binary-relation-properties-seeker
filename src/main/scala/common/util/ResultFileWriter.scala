package com.yurwar
package common.util

import common.entity.Relation

import java.io.{BufferedWriter, PrintWriter}

class ResultFileWriter {
  def writeRelations(fileName: String, relations: List[Relation]): Unit = {
    val bufferedFile = new BufferedWriter(new PrintWriter(fileName))

    relations.indices
      .foreach(idx => {
        bufferedFile.append(s" ${idx + 1}\n")
        relations(idx).matrix.map(line => line.map {
          case true => "1"
          case false => "0"
        }).foreach(line => {
          bufferedFile.append(s" ${line.mkString("  ")}\n")
        })
      })
    bufferedFile.flush()
    bufferedFile.close()
  }
}
