package org.scalax.semweb.picklers

import org.scalajs.spickling.PicklerRegistry
import org.scalajs.spickling.PicklerRegistry._
import org.scalax.semweb.rdf.{BlankNode, IRI, StringLiteral, Trip, _}
import org.scalax.semweb.shex.validation.{JustFailure, Valid}
import org.scalax.semweb.shex.{ArcRule, PropertyModel, Shape, _}
import org.scalax.semweb.sparql.Pat

trait RDFPicklers extends CommonPicklers{

  self:PicklerRegistry=>

  def registerRdfValues() = {
    //Semantic
    register[IRI]
    register[BlankNode]
    register[StringLiteral]
    register[BooleanLiteral]
    register[DoubleLiteral]
    register[DecimalLiteral]
    register[StringLangLiteral]
    register[LongLiteral]
    register[IntegerLiteral]
    register[DateLiteral]
    register[AnyLit]


  }

  def registerStatements() = {
    register[Pat]
    register[Trip]
    register[Quad]

    register(Valid)
    register[JustFailure]

    //register[Map[IRI,RDFValue]]
    register[PropertyModel]

  }

  def registerShapes() = {

    register(Bound.Zero)
    register(Bound.Once)
    register(Bound.Unbound)
    register[Bound.Bounded]

    register(ExactlyOne)
    register(Plus)
    register(Star)
    register(Opt)

    register[Range]
    register[AndRule]
    register[ArcRule]

    register[NameTerm]
    register[NameStem]
    register[NameAny]

    register[ValueAny]
    register[ValueStem]
    register[ValueSet]
    register[ValueReference]
    register[ValueType]


    register[IRILabel]
    register[BNodeLabel]
    register[IRIStem]
    register[Action]

    register[Shape]

  }


  def registerRdf() = {

    this.registerRdfValues()
    this.registerStatements()
    this.registerShapes()






  }

}