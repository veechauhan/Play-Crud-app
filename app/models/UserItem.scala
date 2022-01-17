package models

import play.api.libs.json.{ Json, OFormat }

case class UserItem(id: Option[String], name: String, password: String)

object UserItemFormatter {
  implicit val UserItemFormatter: OFormat[UserItem] = Json.format[UserItem]
}
