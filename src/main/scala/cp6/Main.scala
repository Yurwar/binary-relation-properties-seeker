package com.yurwar
package cp6

import cp6.solver.{FuzzyRelationOperationsSolver, RationalChoiceFindingSolver}

object Main extends App {
  println("==================== 1 TASK SOLVE ====================")
  new FuzzyRelationOperationsSolver().solve()
  println("==================== 2 TASK SOLVE ====================")
  new RationalChoiceFindingSolver().solve()
}
