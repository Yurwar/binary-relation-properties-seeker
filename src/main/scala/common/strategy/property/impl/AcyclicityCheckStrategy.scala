package com.yurwar
package common.strategy.property.impl

import common.entity.RelationProperty.Acyclicity
import common.entity.{PropertyCheckResult, PropertyViolation, Relation}
import common.strategy.property.PropertyCheckStrategy

import scala.collection.mutable

class AcyclicityCheckStrategy extends PropertyCheckStrategy {
  private val antiReflexivityCheckStrategy = new AntiReflexivityCheckStrategy

  override def check(relation: Relation): PropertyCheckResult = {
    val present = antiReflexivityCheckStrategy.check(relation).present && Range(0, relation.size)
      .forall(idx => !cycleExistForRoot(relation, idx))

    new PropertyCheckResult(false, new PropertyViolation(Acyclicity, List.empty))
  }

  private def cycleExistForRoot(relation: Relation, idx: Int): Boolean = {
    val solutionStack: mutable.Stack[Int] = new mutable.Stack[Int]()
    val variantsStack: mutable.Stack[mutable.Stack[Int]] = new mutable.Stack[mutable.Stack[Int]]()

    solutionStack.push(idx)
    variantsStack.push(createStack(relation.getLowerSection(idx)))

    while (!isStackCycled(solutionStack, idx) && solutionStack.nonEmpty) {
      val maybeIndex = getNextElement(variantsStack)
      if (maybeIndex.isDefined) {
        addNextVariants(relation, variantsStack, solutionStack, maybeIndex.get, idx)
      } else {
        solutionStack.pop()
      }
    }

    isStackCycled(solutionStack, idx)
  }

  private def getNextElement(variantsStack: mutable.Stack[mutable.Stack[Int]]): Option[Int] = {
    Option(variantsStack.pop())
      .filter(_.nonEmpty)
      .map(getNextElementFromStack(variantsStack, _))
  }

  private def getNextElementFromStack(variantsStack: mutable.Stack[mutable.Stack[Int]], stack: mutable.Stack[Int]) = {
    val res = stack.pop()
    variantsStack.push(createStack(stack.toList))
    res
  }

  private def addNextVariants(relation: Relation, variantsStack: mutable.Stack[mutable.Stack[Int]],
                              solutionStack: mutable.Stack[Int], index: Int, rootIndex: Int): Unit = {
    solutionStack.push(index)
    variantsStack.push(createStack(relation.getLowerSection(index)
      .filter(i => !solutionStack.contains(i) || i == rootIndex)))
  }

  private def createStack(seq: Seq[Int]): mutable.Stack[Int] = {
    val res: mutable.Stack[Int] = new mutable.Stack[Int]
    seq.foreach(res.push)
    res
  }

  private def isStackCycled(solutionStack: mutable.Stack[Int], idx: Int): Boolean = {
    solutionStack.lastIndexOf(idx) > 0
  }
}
