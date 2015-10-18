package models

import java.util.UUID

object ContactType extends Enumeration {
  type ContactType = Value
  val Landline, Mobile = Value
}

object HoldingType extends Enumeration {
  type HoldingType = Value
  val CheckingAccount, InterestAccount = Value
}

object PartyRelationshipType extends Enumeration {
  type PartyRelationshipType = Value
  val Director, CompanySecretary, RelationshipManager = Value
}

sealed trait Vertex
sealed trait Edge

trait Party extends Vertex {
  def ID: UUID
  def partyID: String
  def contactDetails: Set[ContactDetails]
}

// need to take care not to load the world!

case class Person(ID: UUID, partyID: String, contactDetails: Set[ContactDetails], 
    owns: Set[ProductHolding], 
    firstname: Option[String], 
    secondname: String, 
    relationshipManagerFor: Set[Person], 
    relationshipManagerOf: Person) extends Party

case class Organisation(ID: UUID, partyID: String, contactDetails: Set[ContactDetails], 
    orgName: String, 
    regNum: String, 
    subsiduaryOf: Organisation, 
    parentOf: Set[Organisation]) extends Party

case class ContactDetails(ID: UUID, 
    email: String, 
    phoneNumber: String, 
    contactType: ContactType.Value, 
    belongsTo: Party) extends Vertex

case class ProductHolding(ID: UUID, 
    holdingID: String, 
    holdingType: HoldingType.Value, 
    ownedBy: Set[Party]) extends Vertex

case class PartyContactDetails(ID: UUID) extends Edge

case class PartyRelationship(ID: UUID, partyRelationshipType: PartyRelationshipType.Value) extends Edge


