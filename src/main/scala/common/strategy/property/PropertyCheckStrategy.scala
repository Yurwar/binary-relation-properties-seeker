package com.yurwar
package common.strategy.property

import common.entity.{PropertyCheckResult, Relation}

trait PropertyCheckStrategy {
  def check(relation: Relation): PropertyCheckResult
}
