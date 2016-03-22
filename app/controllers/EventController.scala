package controllers

/**
  * Created by kentac55 on 16/03/19.
  */

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject._
import slick.driver.H2Driver.api._
import org.joda.time.DateTime
import java.sql.Timestamp
import play.api.data.format.Formats._
import scala.concurrent.Future

import EventController._

// TODO - シングルトンってなんぞや（アノテーション取っても動くんだけど!?）
@Singleton()
class EventController @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                                 val messagesApi: MessagesApi) extends Controller
  with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  /**
    * 一覧表示
    * @return
    */
  def list = Action.async { implicit rs =>
    db.run(Event.sortBy(t => t.eventid).result).flatMap { events =>
      db.run(Company.sortBy(_.companyid).result).flatMap { companies =>
        db.run(Type.sortBy(_.typeid).result).map { types =>
          Ok(views.html.event.list(events, companies, types))
        }
      }
    }
  }

  /**
    * 編集画面表示
    * @param eventID
    * @return
    */
  def edit(eventID: Option[Long]) = Action.async { implicit rs =>
    val form = if(eventID.isDefined) {
      // _.eventidはint, eventIDはLong 型を揃える必要がある
      // eventID.get を使っているのはeventID.isDefinedでNoneではないことが確定している為
      db.run(Event.filter(_.eventid === eventID.get.toInt.bind).result.head).map { event =>
        eventForm.fill(EventForm(
          Some(event.eventid),
          event.companyid,
          event.typeid,
          // TODO - ここmodels.TableによるとoptionだからSome()にすべきなんだけど何故か動く
          event.description,
          // java.sql.timestampをjodaへ
          sqlTimestampToDateTime(event.createdate),
          sqlTimestampToDateTime(event.duedate),
          event.ready,
          event.redume,
          event.es
        ))
      }
    } else {
      Future { eventForm }
    }
    // futureを2回以上mapする時は最後のmap以外flatMap
    form.flatMap { form =>
      db.run(Company.sortBy(_.companyid).result).flatMap { companies =>
        db.run(Type.sortBy(_.typeid).result).map { types =>
          Ok(views.html.event.edit(form, companies, types))
        }
      }
    }
  }

  /**
    * 登録実行
    * @return
    */
  def create = TODO

  /**
    * 更新実行
    * @return
    */
  def update = TODO

  /**
    * 削除実行
    * @param id
    * @return
    */
  def remove(id: Long) = TODO
}

object EventController {
  // 便利ツール
  // http://qiita.com/mather314/items/1d0e3bb2e94283f85e96
  def dateTimeToSqlTimestamp: DateTime =>
    Timestamp = { dt => new Timestamp(dt.getMillis) }
  def sqlTimestampToDateTime: Timestamp =>
    DateTime = { ts => new DateTime(ts.getTime) }

  case class EventForm(
                        eventid: Option[Int],
                        companyid: Int,
                        typeid: Int,
                        description: Option[String],
                        createdate: DateTime,
                        duedate: DateTime,
                        ready: Boolean,
                        redume: Boolean,
                        es: Boolean
                      )

  val eventForm = Form(
    // 型がないのでjodaる
    // ココらへんはplay.api.data.Formを熟読
    // 上のEventFormと型を合わせる必要アリ
    mapping(
      "eventid"     -> optional(number),
      "companyid"   -> of[Int],
      "typeid"      -> of[Int],
      "description" -> optional(text),
      "createdate"  -> jodaDate("yyyy-MM-dd hh:mm:ss"),
      "duedate"     -> jodaDate("yyyy-MM-dd hh:mm:ss"),
      "ready"       -> of[Boolean],
      "redume"      -> of[Boolean],
      "es"          -> of[Boolean]
    )(EventForm.apply)(EventForm.unapply)
  )
}