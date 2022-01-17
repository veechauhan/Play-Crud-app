package models

import com.google.inject.Inject
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.{ ExecutionContext, Future }

class UserTableDef(tag: Tag) extends Table[UserItem](tag, "users") {

  def id: Rep[String]       = column[String]("id", O.PrimaryKey)
  def name: Rep[String]     = column[String]("name")
  def password: Rep[String] = column[String]("password")

  override def * : ProvenShape[UserItem] = (id.?, name, password) <> (UserItem.tupled, UserItem.unapply)

}

class UserRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit
  ec: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  val users = TableQuery[UserTableDef]

  def add(userItem: UserItem): Future[Int] =
    dbConfig.db.run(users += userItem)

  def getById(id: String): Future[Option[UserItem]] =
    dbConfig.db.run(users.filter(_.id === id).result.headOption)

  def getAll: Future[Seq[UserItem]] =
    dbConfig.db.run(users.result)

  def delete(id: String): Future[Int] =
    dbConfig.db.run(users.filter(_.id === id).delete)

  def update(id: String, name: String, password: String): Future[Int] =
    dbConfig.db.run(users.filter(_.id === id).map(user => (user.name, user.password)).update(name, password))

}
