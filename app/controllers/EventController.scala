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

import com.google.inject.{AbstractModule, Guice}
import play.api.inject.Injector

import scala.concurrent.Future
import slick.driver.H2Driver.api._

@Singleton()
class EventController @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                                 val messagesApi: MessagesApi) extends Controller
  with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  /**
    * 一覧表示
    * @return
    */
  def list = Action.async { implicit rs =>
    db.run(Event.sortBy(t => t.eventid).result).map { events =>
      Ok(views.html.event.list(events))
    }
  }

  /**
    * 編集画面表示
    * @param id
    * @return
    */
  def edit(id: Option[Long]) = TODO

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
