package com.yurwar
package common.service

import common.entity.RelationProperty.RelationProperty
import common.entity.{FuzzyRelation, PropertyCheckResult}

trait FuzzyRelationService {
  def getStrictPreference(relation: FuzzyRelation): FuzzyRelation

  def findPreferableAlternatives(relation: FuzzyRelation): List[Int]

  def findPropertiesForRelation(relation: FuzzyRelation): List[RelationProperty]

  def getPropertiesCheckResults(relation: FuzzyRelation): List[PropertyCheckResult]
}
