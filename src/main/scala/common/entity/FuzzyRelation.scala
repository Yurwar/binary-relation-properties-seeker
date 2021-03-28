package com.yurwar
package common.entity

case class FuzzyRelation(matrix: List[List[Double]]) {
  def union(that: FuzzyRelation): FuzzyRelation = {
    if (this.matrix.indices != that.matrix.indices) {
      null
    } else {
      FuzzyRelation(this.matrix.indices.map(row =>
        this.matrix.indices.map(col => {
          math.max(this.matrix(row)(col), that.matrix(row)(col))
        }).toList).toList)
    }
  }

  def intersect(that: FuzzyRelation): FuzzyRelation = {
    if (this.matrix.indices != that.matrix.indices) {
      null
    } else {
      FuzzyRelation(this.matrix.indices.map(row =>
        this.matrix.indices.map(col => {
          math.min(this.matrix(row)(col), that.matrix(row)(col))
        }).toList).toList)
    }
  }

  def complement(): FuzzyRelation = {
    FuzzyRelation(matrix.map(row => row.map(el => 1 - el)))
  }

  def compose(that: FuzzyRelation): FuzzyRelation = {
    if (this.matrix.indices != that.matrix.indices) {
      null
    } else {
      FuzzyRelation(matrix.indices.map(row => {
        matrix.indices.map(col => {
          getRow(row, this.matrix)
            .zip(getColumn(col, that.matrix))
            .map(pair => math.min(pair._1, pair._2))
            .max
        }).toList
      }).toList)
    }
  }

  private def getColumn(col: Int, matrix: List[List[Double]]): List[Double] = {
    matrix.indices
      .map(idx => matrix(idx)(col)).toList
  }

  private def getRow(row: Int, matrix: List[List[Double]]): List[Double] = {
    matrix(row)
  }

  def subsetOf(that: FuzzyRelation): Boolean = {
    if (this.matrix.indices != that.matrix.indices) {
      throw new IllegalArgumentException
    } else {
      this.matrix.indices.forall(row => {
        this.matrix.indices.forall(col => {
          this.matrix(row)(col) <= that.matrix(row)(col)
        })
      })
    }
  }

  def converse(): FuzzyRelation = {
    FuzzyRelation(matrix.transpose)
  }

  def alphaLevel(level: Double): Relation = {
    new Relation(matrix.map(row => row.map(el => el >= level)))
  }
}
