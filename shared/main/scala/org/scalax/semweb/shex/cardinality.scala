package org.scalax.semweb.shex

import org.scalax.semweb.rdf.vocabulary._
import org.scalax.semweb.rdf.{LongLiteral, Quad, Res}


object Bound {
  def apply(limit:Long) = new Bound(limit)
  case object Zero extends Bound(0)
  case object Once extends Bound(1)
  case object Unbound extends Bound(Long.MaxValue)


}
class Bound(val limit:Long) {
  def isUnbound = limit==Long.MaxValue
  def isZero = limit==0
}


abstract class Cardinality(min: Bound,max: Bound) extends ToQuads
{
  require(min.limit<=max.limit) //check that minimum is lower that maximum
}

object Range {
  val minProperty = rs / "minoccurs"
  val maxProperty = rs / "maxoccurs"
}

case class Range(min:Long,max:Long) extends Cardinality(Bound(min),Bound(max))
{
  override def toQuads(subject: Res)(implicit context: Res): Set[Quad] = Set(
    Quad(subject,Range.minProperty,LongLiteral(min),context),
    Quad(subject,Range.maxProperty,LongLiteral(max),context)
  )
}


object Cardinality
{
  //def apply(min: Bound,max: Bound) = new Cardinality(min,max)
  def apply(min: Long,max:Long) = Range(min,max)

}



// Utility definitions


import Bound._

case object ExactlyOne extends Cardinality(Once,Once)
{
  val property = rs / "occurs"
  val obj = rs / "Exactly-one"

  override def toQuads(subject: Res)(implicit context: Res): Set[Quad] = Set(
    Quad(subject,property,obj,context)
  )

}

case object Plus extends Cardinality(Once,Unbound)
{
  val property = rs / "occurs"
  val obj = rs / "One-or-many"

  override def toQuads(subject: Res)(implicit context: Res): Set[Quad] = Set(
    Quad(subject,this.property,rs / "One-or-many",context)
  )

}

case object Star  extends Cardinality(Zero,Unbound) {
  val property = rs / "occurs"
  val obj = rs / "Zero-or-many"

  override def toQuads(subject: Res)(implicit context: Res): Set[Quad] = Set(
    Quad(subject,property,rs / obj,context)
  )

}

case object Opt extends Cardinality(Zero,Once) {

  val property = rs / "occurs"
  val obj = rs / "Zero-or-one"

  override def toQuads(subject: Res)(implicit context: Res): Set[Quad] = Set(
    Quad(subject,property,rs / obj,context)
  )

}

// lazy val NoId : Label =Label(iri =(""))