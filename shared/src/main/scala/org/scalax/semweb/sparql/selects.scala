package org.scalax.semweb.sparql

import org.scalax.semweb.rdf.{IRI, RDFElement}


object SELECT
{
  //def apply(params:SelectElement*): SelectQuery = new SelectQuery(params = params.toList)

  def apply(params:SelectElement*): SelectQuery = SelectQuery(params)
}


case class SelectQuery(params:Seq[SelectElement],from:String = "") extends WithWhere with Sliced
{
  lazy val vars: Map[String, Variable] = params.collect{case v:Variable=>v.name->v}.toMap

  protected lazy val selection = params.foldLeft("")( (acc,el)=>acc+" "+el.stringValue)

  def FROM(iri:IRI):SelectQuery =  this.copy(from= from + s"FROM ${iri.toString} ") //DIRTY but working

  def FROM_NAMED(iri:IRI):SelectQuery  =  this.copy(from= from + s"FROM NAMED ${iri.toString} ")


  def stringValue = s"SELECT $selection $from\n${WHERE.stringValue}\n ${LIMIT.stringValue} ${OFFSET.stringValue}"

}

trait Sliced {
  self=>

  /**
   * Limit object
   */
  object LIMIT extends RDFElement
  {
    var limit:Long = Long.MaxValue
    def apply(limitValue:Long):self.type = {
      this.limit = limitValue
      self
    }

    def hasLimit:Boolean = this.limit!=Long.MaxValue && limit>0


    override def stringValue = if(hasLimit) s" LIMIT $limit" else ""
  }

  object OFFSET extends RDFElement
  {
    var offset:Long = 0
    def apply(offsetValue:Long):self.type  = {
      this.offset = offsetValue
      self
    }

    def hasOffset:Boolean = this.offset>0


    override def stringValue = if(this.hasOffset) s" OFFSET $offset" else ""
  }
}
