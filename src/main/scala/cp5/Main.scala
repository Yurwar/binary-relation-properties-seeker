package com.yurwar
package cp5

import cp5.solver.{TopsisRankFindingSolver, VikorRankFindingSolver}

object Main extends App {
  println("==================== 1 TASK SOLVE ====================")
  new TopsisRankFindingSolver().solve()
  println("==================== 2 TASK SOLVE ====================")
  new VikorRankFindingSolver().solve()
}
