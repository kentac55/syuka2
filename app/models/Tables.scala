package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Company.schema ++ Event.schema ++ Type.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Company
   *  @param companyid Database column COMPANYID SqlType(INTEGER), PrimaryKey
   *  @param companyname Database column COMPANYNAME SqlType(VARCHAR)
   *  @param etc Database column ETC SqlType(VARCHAR)
   *  @param upcomingeventid Database column UPCOMINGEVENTID SqlType(INTEGER)
   *  @param prayed Database column PRAYED SqlType(INTEGER) */
  case class CompanyRow(companyid: Int, companyname: Option[String], etc: Option[String], upcomingeventid: Option[Int], prayed: Option[Int])
  /** GetResult implicit for fetching CompanyRow objects using plain SQL queries */
  implicit def GetResultCompanyRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]]): GR[CompanyRow] = GR{
    prs => import prs._
    CompanyRow.tupled((<<[Int], <<?[String], <<?[String], <<?[Int], <<?[Int]))
  }
  /** Table description of table COMPANY. Objects of this class serve as prototypes for rows in queries. */
  class Company(_tableTag: Tag) extends Table[CompanyRow](_tableTag, "COMPANY") {
    def * = (companyid, companyname, etc, upcomingeventid, prayed) <> (CompanyRow.tupled, CompanyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(companyid), companyname, etc, upcomingeventid, prayed).shaped.<>({r=>import r._; _1.map(_=> CompanyRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column COMPANYID SqlType(INTEGER), PrimaryKey */
    val companyid: Rep[Int] = column[Int]("COMPANYID", O.PrimaryKey)
    /** Database column COMPANYNAME SqlType(VARCHAR) */
    val companyname: Rep[Option[String]] = column[Option[String]]("COMPANYNAME")
    /** Database column ETC SqlType(VARCHAR) */
    val etc: Rep[Option[String]] = column[Option[String]]("ETC")
    /** Database column UPCOMINGEVENTID SqlType(INTEGER) */
    val upcomingeventid: Rep[Option[Int]] = column[Option[Int]]("UPCOMINGEVENTID")
    /** Database column PRAYED SqlType(INTEGER) */
    val prayed: Rep[Option[Int]] = column[Option[Int]]("PRAYED")
  }
  /** Collection-like TableQuery object for table Company */
  lazy val Company = new TableQuery(tag => new Company(tag))

  /** Entity class storing rows of table Event
   *  @param eventid Database column EVENTID SqlType(INTEGER), PrimaryKey
   *  @param companyid Database column COMPANYID SqlType(INTEGER)
   *  @param typeid Database column TYPEID SqlType(INTEGER)
   *  @param description Database column DESCRIPTION SqlType(VARCHAR)
   *  @param createdate Database column CREATEDATE SqlType(TIMESTAMP)
   *  @param duedate Database column DUEDATE SqlType(TIMESTAMP)
   *  @param ready Database column READY SqlType(BOOLEAN)
   *  @param redume Database column REDUME SqlType(BOOLEAN)
   *  @param es Database column ES SqlType(BOOLEAN) */
  case class EventRow(eventid: Int, companyid: Option[Int], typeid: Option[Int], description: Option[String], createdate: Option[java.sql.Timestamp], duedate: Option[java.sql.Timestamp], ready: Option[Boolean], redume: Option[Boolean], es: Option[Boolean])
  /** GetResult implicit for fetching EventRow objects using plain SQL queries */
  implicit def GetResultEventRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]], e4: GR[Option[Boolean]]): GR[EventRow] = GR{
    prs => import prs._
    EventRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[Boolean], <<?[Boolean], <<?[Boolean]))
  }
  /** Table description of table EVENT. Objects of this class serve as prototypes for rows in queries. */
  class Event(_tableTag: Tag) extends Table[EventRow](_tableTag, "EVENT") {
    def * = (eventid, companyid, typeid, description, createdate, duedate, ready, redume, es) <> (EventRow.tupled, EventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(eventid), companyid, typeid, description, createdate, duedate, ready, redume, es).shaped.<>({r=>import r._; _1.map(_=> EventRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column EVENTID SqlType(INTEGER), PrimaryKey */
    val eventid: Rep[Int] = column[Int]("EVENTID", O.PrimaryKey)
    /** Database column COMPANYID SqlType(INTEGER) */
    val companyid: Rep[Option[Int]] = column[Option[Int]]("COMPANYID")
    /** Database column TYPEID SqlType(INTEGER) */
    val typeid: Rep[Option[Int]] = column[Option[Int]]("TYPEID")
    /** Database column DESCRIPTION SqlType(VARCHAR) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")
    /** Database column CREATEDATE SqlType(TIMESTAMP) */
    val createdate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("CREATEDATE")
    /** Database column DUEDATE SqlType(TIMESTAMP) */
    val duedate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("DUEDATE")
    /** Database column READY SqlType(BOOLEAN) */
    val ready: Rep[Option[Boolean]] = column[Option[Boolean]]("READY")
    /** Database column REDUME SqlType(BOOLEAN) */
    val redume: Rep[Option[Boolean]] = column[Option[Boolean]]("REDUME")
    /** Database column ES SqlType(BOOLEAN) */
    val es: Rep[Option[Boolean]] = column[Option[Boolean]]("ES")
  }
  /** Collection-like TableQuery object for table Event */
  lazy val Event = new TableQuery(tag => new Event(tag))

  /** Entity class storing rows of table Type
   *  @param typeid Database column TYPEID SqlType(INTEGER)
   *  @param typename Database column TYPENAME SqlType(VARCHAR) */
  case class TypeRow(typeid: Option[Int], typename: Option[String])
  /** GetResult implicit for fetching TypeRow objects using plain SQL queries */
  implicit def GetResultTypeRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[TypeRow] = GR{
    prs => import prs._
    TypeRow.tupled((<<?[Int], <<?[String]))
  }
  /** Table description of table TYPE. Objects of this class serve as prototypes for rows in queries. */
  class Type(_tableTag: Tag) extends Table[TypeRow](_tableTag, "TYPE") {
    def * = (typeid, typename) <> (TypeRow.tupled, TypeRow.unapply)

    /** Database column TYPEID SqlType(INTEGER) */
    val typeid: Rep[Option[Int]] = column[Option[Int]]("TYPEID")
    /** Database column TYPENAME SqlType(VARCHAR) */
    val typename: Rep[Option[String]] = column[Option[String]]("TYPENAME")
  }
  /** Collection-like TableQuery object for table Type */
  lazy val Type = new TableQuery(tag => new Type(tag))
}
