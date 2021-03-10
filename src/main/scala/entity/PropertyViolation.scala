package com.yurwar
package entity

import entity.RelationProperty.RelationProperty

class PropertyViolation(var relationProperty: RelationProperty, val violationPoints: List[(Int, Int)]) {

}
