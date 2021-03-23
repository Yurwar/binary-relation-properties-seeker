package com.yurwar
package common.strategy.criteria.impl

import common.entity.{SimpleCriteria, Relation}
import common.strategy.criteria.RelationBuildingStrategy
import cp2.entity.{BerezovskyRelationSystem, ParetoRelationSystem}

import scala.annotation.tailrec

class BerezovskyRelationBuildingStrategy extends RelationBuildingStrategy {

  override def buildByCriteria(criteria: SimpleCriteria): Relation = {
    val paretoRelationSystems = criteria.quasiAscCriterionGroupOrder
      .map(equalCriteria => buildParetoRelationSystem(equalCriteria, criteria))

    val berezovskyRelationSystem = iterateBerezovskyRelationSystem(paretoRelationSystems)

    new Relation(berezovskyRelationSystem.p)
  }

  private def buildParetoRelationSystem(equalCriteria: List[Int], criteria: SimpleCriteria): ParetoRelationSystem = {
    val paretoRelationBuildChain = buildParetoRelation(equalCriteria, criteria)(_: List[Int] => Boolean)

    val paretoPRelation = paretoRelationBuildChain(deltaSign => deltaSign.contains(1) && deltaSign.forall(_ >= 0))
    val paretoIRelation = paretoRelationBuildChain(_.forall(_ == 0))
    val paretoNRelation = paretoRelationBuildChain(deltaSign => deltaSign.contains(1) && deltaSign.contains(-1))

    ParetoRelationSystem(paretoPRelation,
      paretoIRelation,
      paretoNRelation)
  }

  private def buildParetoRelation(equalCriteria: List[Int], criteria: SimpleCriteria)(checkParetoProperty: List[Int] => Boolean): List[List[Boolean]] = {
    criteria.alternativesRating.indices
      .map(row => criteria.alternativesRating.indices.map(col => {
        val deltaSign = equalCriteria.map(criterionIdx => {
          (criteria.alternativesRating(row)(criterionIdx) - criteria.alternativesRating(col)(criterionIdx)).compareTo(0)
        })
        checkParetoProperty(deltaSign)
      }).toList).toList
  }

  private def buildBerezovskyPRelation(paretoRelation: ParetoRelationSystem, berezovskyRelation: BerezovskyRelationSystem): List[List[Boolean]] = {
    paretoRelation.p.indices.map(x => paretoRelation.p.indices.map(y => {
      (paretoRelation.p(x)(y) &&
        (berezovskyRelation.p(x)(y) ||
          berezovskyRelation.n(x)(y) ||
          berezovskyRelation.i(x)(y))) ||
        (paretoRelation.i(x)(y) && berezovskyRelation.p(x)(y))
    }).toList).toList
  }

  private def buildBerezovskyIRelation(paretoRelation: ParetoRelationSystem, berezovskyRelation: BerezovskyRelationSystem): List[List[Boolean]] = {
    paretoRelation.p.indices.map(x => paretoRelation.p.indices.map(y => {
      paretoRelation.i(x)(y) && berezovskyRelation.i(x)(y)
    }).toList).toList
  }

  private def buildBerezovskyNRelation(paretoRelation: ParetoRelationSystem,
                                       berezovskyPRelation: List[List[Boolean]],
                                       berezovskyIRelation: List[List[Boolean]]): List[List[Boolean]] = {
    paretoRelation.p.indices.map(x => paretoRelation.p.indices.map(y => {
      !(berezovskyPRelation(x)(y) || berezovskyPRelation(y)(x) || berezovskyIRelation(x)(y))
    }).toList).toList
  }

  private def iterateBerezovskyRelationSystem(paretoSystems: List[ParetoRelationSystem]): BerezovskyRelationSystem = {
    val resBSystem = BerezovskyRelationSystem(paretoSystems.head.p, paretoSystems.head.i, paretoSystems.head.n)

    @tailrec
    def iterateBSystemInternal(paretoSystems: List[ParetoRelationSystem], currentBSystem: BerezovskyRelationSystem): BerezovskyRelationSystem = {
      if (paretoSystems.isEmpty) {
        currentBSystem
      } else {
        val currentPSystem = paretoSystems.head

        val bPRelation = buildBerezovskyPRelation(currentPSystem, currentBSystem)
        val bIRelation = buildBerezovskyIRelation(currentPSystem, currentBSystem)
        val bNRelation = buildBerezovskyNRelation(currentPSystem, bPRelation, bIRelation)

        val nextBSystem = BerezovskyRelationSystem(bPRelation, bIRelation, bNRelation)

        paretoSystems match {
          case _ :: Nil => iterateBSystemInternal(Nil, nextBSystem)
          case _ :: tail => iterateBSystemInternal(tail, nextBSystem)
        }
      }
    }

    iterateBSystemInternal(paretoSystems.tail, resBSystem)
  }
}
