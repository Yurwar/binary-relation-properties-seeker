package com.yurwar
package cp2.entity

sealed class RelationSystem(p: List[List[Boolean]],
                            i: List[List[Boolean]],
                            n: List[List[Boolean]])

sealed case class ParetoRelationSystem(p: List[List[Boolean]],
                                       i: List[List[Boolean]],
                                       n: List[List[Boolean]]) extends RelationSystem(p, i, n)

sealed case class BerezovskyRelationSystem(p: List[List[Boolean]],
                                           i: List[List[Boolean]],
                                           n: List[List[Boolean]]) extends RelationSystem(p, i, n)
