package com.yurwar
package common.util

import common.entity.Relation

import java.io.{BufferedWriter, PrintWriter}

class ResultFileWriter(val fileName: String) {
  def writeRelations(relations: List[Relation]): Unit = {
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

  def writeIndexMatrix(idxType: String, index: List[List[Double]]): Unit = {
    val bufferedFile = new BufferedWriter(new PrintWriter(fileName))

    bufferedFile.write(idxType + " index:")
    bufferedFile.newLine()

    for (i <- index.indices) {
      bufferedFile.write(index(i).map("%.3f".format(_)).mkString(" "))
      bufferedFile.newLine()
    }

    bufferedFile.flush()
    bufferedFile.close()
  }

  def writeText(text: String): Unit = {
    val bufferedFile = new BufferedWriter(new PrintWriter(fileName))

    bufferedFile.write(text)

    bufferedFile.flush()
    bufferedFile.close()
  }
}
