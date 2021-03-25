package com.yurwar
package common.entity

sealed class CriterionType

sealed case class BenefitCriterion() extends CriterionType {
  override def toString: String = "Benefit"
}

sealed case class CostCriterion() extends CriterionType {
  override def toString: String = "Cost"
}