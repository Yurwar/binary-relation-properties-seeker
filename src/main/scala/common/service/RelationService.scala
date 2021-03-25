package com.yurwar
package common.service

import common.entity.Relation
import common.entity.RelationClasses.RelationClass
import common.entity.RelationProperty.RelationProperty

trait RelationService {

  def findClassForRelation(relation: Relation): RelationClass

  def findPropertiesForRelation(relation: Relation): List[RelationProperty]

  def fulfillRelationFields(relation: Relation): Relation
}
