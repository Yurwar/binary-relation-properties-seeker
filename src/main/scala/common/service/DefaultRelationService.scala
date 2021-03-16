package com.yurwar
package common.service

import common.entity.RelationClasses.RelationClass
import common.entity.RelationProperty.RelationProperty
import common.entity.{Relation, RelationClasses}
import common.util.Utils

import scala.collection.mutable.ListBuffer

class DefaultRelationService extends RelationService {


  override def findClassForRelation(relation: Relation): RelationClass = {
    RelationClasses.values
      .toList
      .sortBy(_.props.size)
      .reverse
      .foreach(rc => {
        if (rc.props.forall(relation.relationProperties.contains)) {
          return rc
        }
      })

    RelationClasses.Undefined
  }

  override def findPropertiesForRelation(relation: Relation): List[RelationProperty] = {
    val props = new ListBuffer[RelationProperty]
    Utils.PropertyStrategies.foreach(propStrategy => {
      val checkResult = propStrategy._2.check(relation)
      if (checkResult.present) {
        props.addOne(propStrategy._1)
      }
    })
    props.toList
  }
}
