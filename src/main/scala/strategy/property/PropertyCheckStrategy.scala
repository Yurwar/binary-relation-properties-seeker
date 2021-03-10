package com.yurwar
package strategy.property

import entity.{PropertyCheckResult, Relation}

trait PropertyCheckStrategy {
  def check(relation: Relation): PropertyCheckResult
}
