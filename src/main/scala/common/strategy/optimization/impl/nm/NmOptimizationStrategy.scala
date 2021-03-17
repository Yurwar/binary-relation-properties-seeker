package com.yurwar
package common.strategy.optimization.impl.nm

import common.entity.Relation
import common.entity.RelationProperty.Acyclicity
import common.strategy.optimization.AbstractOptimizationStrategy

class NmOptimizationStrategy extends AbstractOptimizationStrategy(Set(Acyclicity), Set.empty) {

  override def getOptimalElements(rel: Relation): List[Int] = {
    getSequenceQ(rel).last.toList
  }

  private def getInitialQ(relation: Relation): Set[Int] = {
    getInitialS(relation)
  }

  private def getSequenceQ(relation: Relation): List[Set[Int]] = {
    val sequenceQ: List[Set[Int]] = List(getInitialQ(relation))
    val sequenceS: List[Set[Int]] = getSequenceS(relation, getInitialS(relation))

    def getSequenceQNested(relation: Relation,
                           seqQ: List[Set[Int]], seqS: List[Set[Int]],
                           currS: Set[Int]): List[Set[Int]] = {
      if (seqS.isEmpty) {
        seqQ
      } else {
        val diffS = seqS.head.diff(currS)

        val nextQ = seqQ.last.union(relation.matrix.indices
          .filter(el => diffS.contains(el))
          .filter(el => {
            relation.getUpperSection(el).toSet.intersect(seqQ.last).isEmpty
          }).toSet)

        seqQ ::: getSequenceQNested(relation, seqQ ::: List(nextQ), seqS.tail, seqS.head)
      }
    }

    getSequenceQNested(relation, List(sequenceQ.head), sequenceS.tail, sequenceS.head)
  }

  private def getInitialS(relation: Relation): Set[Int] = {
    relation.matrix.indices
      .filter(relation.getUpperSection(_).isEmpty)
      .toSet
  }

  private def getSequenceS(relation: Relation, initialS: Set[Int]): List[Set[Int]] = {
    var sequenceS: List[Set[Int]] = List(initialS)
    var currentS: Set[Int] = Set.from(initialS)

    while (!currentS.equals(relation.matrix.indices.toSet)) {
      currentS = currentS.union(relation.matrix.indices.toSet
        .filter(el => {
          relation.getUpperSection(el).toSet.subsetOf(currentS)
        }))

      sequenceS = sequenceS ::: List(currentS)
    }

    sequenceS
  }

}
