package com.yurwar
package common.entity

import common.entity.RelationProperty.RelationProperty

class PropertyViolation(var relationProperty: RelationProperty, val violationPoints: List[(Int, Int)]) {

}
